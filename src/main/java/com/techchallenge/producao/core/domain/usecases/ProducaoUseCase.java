package com.techchallenge.producao.core.domain.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techchallenge.producao.adapter.gateways.ProducaoGateway;
import com.techchallenge.producao.core.domain.entities.Pedido;

import java.util.List;

@Service
public class ProducaoUseCase {
	
	@Autowired
	private ProducaoGateway gateway;
	
	public void adicionarPedidoAFilaDeProducao(String id) {
		gateway.adicionarPedidoAFilaDeProducao(id);
	}

	public void atualizarStatusDePedidoEmProducao(Pedido pedido) {
		gateway.atualizarStatusDePedidoEmProducao(pedido);
	}

	public List<Pedido> consultarFilaDePedidos() {
		return gateway.consultarFilaDePedidos();
	}

    public Pedido consultarStatusDePedidoEmProducao(String id) {
		return gateway.consultarStatusDePedidoEmProducao(id);
    }

	public List<Pedido> consultarHistoricoDeProducaoDePedido(String id) {
		return gateway.consultarHistoricoDeProducaoDePedido(id);
	}
}