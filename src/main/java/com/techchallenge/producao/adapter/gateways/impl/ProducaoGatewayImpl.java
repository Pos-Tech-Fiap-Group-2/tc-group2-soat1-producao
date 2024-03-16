package com.techchallenge.producao.adapter.gateways.impl;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.driver.messaging.producer.cliente.NotificacaoClienteProducer;
import com.techchallenge.producao.adapter.dto.PedidoDTO;
import com.techchallenge.producao.adapter.external.pedido.PedidoAPI;
import com.techchallenge.producao.adapter.external.pedido.model.StatusPedidoInput;
import com.techchallenge.producao.adapter.gateways.ProducaoGateway;
import com.techchallenge.producao.adapter.mapper.business.ProducaoBusinessMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.core.domain.entities.messaging.Mensagem;
import com.techchallenge.producao.core.domain.entities.messaging.StatusPedido;
import com.techchallenge.producao.drivers.db.entities.PedidoEntity;
import com.techchallenge.producao.drivers.db.repositories.ProducaoRepository;

@Component
public class ProducaoGatewayImpl implements ProducaoGateway {
	
	@Autowired
	private ProducaoRepository pedidosRepository;

	@Autowired
	private ProducaoBusinessMapper mapper;
	
	@Autowired
	private NotificacaoClienteProducer clienteProducer;
	
	@Autowired
	private PedidoAPI pedidoAPI;
	
	@Override
	public void adicionarPedidoAFilaDeProducao(String id) {
		PedidoEntity entity = new PedidoEntity();

		entity.setPedidoId(id);
		entity.setStatus(StatusPedido.RECEBIDO.name());

		pedidosRepository.save(entity);
	}

	@Override
	public void atualizarStatusDePedidoEmProducao(Pedido pedido) {

		PedidoEntity entity = pedidosRepository.findByPedidoId(pedido.getPedidoId());

		if (entity == null) {
			throw new RuntimeException("Pedido não encontrado");
		}

		StatusPedido status = StatusPedido.getStatusPedidoByName(pedido.getStatus());
		
		if (status == null) {
			throw new RuntimeException("Status não suportado");
		}
		
		entity = new PedidoEntity();
		entity.setPedidoId(pedido.getPedidoId());
		entity.setStatus(status.name());

		pedidosRepository.save(entity);
		
		Long pedidoId = Long.valueOf(pedido.getPedidoId());
		
		PedidoDTO pedidoDto = pedidoAPI.buscarDadosPedido(pedidoId);
		// Atualiza para o status atual do pedido.
		pedidoDto.setStatus(status);
		
		StatusPedidoInput atualizacao = new StatusPedidoInput();
		atualizacao.setStatus(status);
		
		
		this.pedidoAPI.atualizarStatusPedido(pedidoId, atualizacao);
		this.enviarNotificacaoCliente(pedidoDto);
	}

	@Override
	public List<Pedido> consultarFilaDePedidos() {
		List<PedidoEntity> pedidos = pedidosRepository.findAllGroupByPedidoIdOrderByDataCriacao();
		return mapper.toCollectionModel(pedidos);
	}

	@Override
	public Pedido consultarStatusDePedidoEmProducao(String id) {
		PedidoEntity entity = pedidosRepository.findByPedidoId(id);
		return mapper.toModel(entity);
	}

	@Override
	public List<Pedido> consultarHistoricoDeProducaoDePedido(String id) {
		List<PedidoEntity> pedidos = pedidosRepository.findAllByPedidoIdOrderByDataCriacao(id);
		return mapper.toCollectionModel(pedidos);
	}
	
	private void enviarNotificacaoCliente(PedidoDTO pedido) {
		
		Mensagem mensagem = new Mensagem();
		mensagem.setCliente(new com.techchallenge.producao.core.domain.entities.messaging.Cliente());
		mensagem.getCliente().setEmail(pedido.getCliente().getEmail());
		mensagem.getCliente().setNome(pedido.getCliente().getNome());
		mensagem.getCliente().setPedido(new com.techchallenge.producao.core.domain.entities.messaging.Pedido());
		mensagem.getCliente().getPedido().setDataSolicitacao(pedido.getDataSolicitacao());
		mensagem.getCliente().getPedido().setId(pedido.getId());
		mensagem.getCliente().getPedido().setStatus(pedido.getStatus());
		mensagem.getCliente().getPedido().setValor(pedido.getValor());
		mensagem.setTemplate(pedido.getStatus().getStatus());
		mensagem.setTimestamp(OffsetDateTime.now());
		
		System.out.println("Enviando mensagem: " + mensagem);
		clienteProducer.enviar(mensagem);
	}
}
