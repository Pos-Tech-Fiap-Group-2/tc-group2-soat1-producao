package com.techchallenge.producao.adapter.gateways.impl;

import com.techchallenge.producao.adapter.external.pedidos.PedidosAPI;
import com.techchallenge.producao.adapter.mapper.business.ProducaoBusinessMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.gateways.ProducaoGateway;
import com.techchallenge.producao.drivers.db.entities.PedidoEntity;
import com.techchallenge.producao.drivers.db.repositories.ProducaoRepository;

import java.io.IOException;
import java.util.List;

@Component
public class ProducaoGatewayImpl implements ProducaoGateway {

	@Autowired
	private ProducaoRepository pedidosRepository;

	@Autowired
	private ProducaoBusinessMapper mapper;

	@Autowired
	private PedidosAPI pedidosAPI;
	
	@Override
	public void adicionarPedidoAFilaDeProducao(String id) {
		PedidoEntity entity = new PedidoEntity();

		entity.setPedidoId(id);
		entity.setStatus("RECEBIDO");

		pedidosRepository.save(entity);
	}

	@Override
	public void atualizarStatusDePedidoEmProducao(Pedido pedido) throws IOException {

		PedidoEntity entity = pedidosRepository.findByPedidoId(pedido.getPedidoId());

		if (entity == null) {
			throw new RuntimeException("Pedido não encontrado");
		}

		entity = new PedidoEntity();
		entity.setPedidoId(pedido.getPedidoId());
		entity.setStatus(pedido.getStatus());

		pedidosRepository.save(entity);

		pedidosAPI.atualizarStatusDePedido(entity.getPedidoId(), entity.getStatus());
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
}
