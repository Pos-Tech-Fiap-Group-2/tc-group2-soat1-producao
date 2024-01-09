package com.techchallenge.producao.adapter.gateways;

import com.techchallenge.producao.core.domain.entities.Pedido;

import java.util.List;

public interface ProducaoGateway {

	void adicionarPedidoAFilaDeProducao(String id);

	void atualizarStatusDePedidoEmProducao(Pedido pedido);

	List<Pedido> consultarFilaDePedidos();

    Pedido consultarStatusDePedidoEmProducao(String id);

	List<Pedido> consultarHistoricoDeProducaoDePedido(String id);
}
