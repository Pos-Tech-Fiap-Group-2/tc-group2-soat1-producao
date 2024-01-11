package com.techchallenge.producao.adapter.gateways;

import com.techchallenge.producao.core.domain.entities.Pedido;

import java.io.IOException;
import java.util.List;

public interface ProducaoGateway {

	void adicionarPedidoAFilaDeProducao(String id);

	void atualizarStatusDePedidoEmProducao(Pedido pedido) throws IOException;

	List<Pedido> consultarFilaDePedidos();

    Pedido consultarStatusDePedidoEmProducao(String id);

	List<Pedido> consultarHistoricoDeProducaoDePedido(String id);
}
