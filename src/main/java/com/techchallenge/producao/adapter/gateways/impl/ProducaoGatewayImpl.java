package com.techchallenge.producao.adapter.gateways.impl;

import com.techchallenge.producao.adapter.mapper.business.ProducaoBusinessMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.gateways.ProducaoGateway;
import com.techchallenge.producao.drivers.db.entities.PedidoEntity;
import com.techchallenge.producao.drivers.db.repositories.ProducaoRepository;

import java.util.List;

@Component
public class ProducaoGatewayImpl implements ProducaoGateway {
	
	@Autowired
	private ProducaoRepository pedidosRepository;

	@Autowired
	private ProducaoBusinessMapper mapper;
	
	@Override
	public void adicionarPedidoAFilaDeProducao(String id) {
		PedidoEntity entity = new PedidoEntity();

		entity.setPedidoId(id);
		entity.setStatus("RECEBIDO");

		pedidosRepository.save(entity);
	}

	@Override
	public void atualizarStatusDePedidoEmProducao(Pedido pedido) {
		PedidoEntity entity = new PedidoEntity();
		entity.setPedidoId(pedido.getPedidoId());
		entity.setStatus(pedido.getStatus());

		pedidosRepository.save(entity);
	}

	@Override
	public List<Pedido> consultarFilaDePedidos() {
	  	List<PedidoEntity> entities = pedidosRepository.findAll();
		return mapper.toCollectionModel(entities);
	}
}
