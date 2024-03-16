package com.techchallenge.producao.adapter.external.pedido.model;

import com.techchallenge.producao.core.domain.entities.messaging.StatusPedido;

public class StatusPedidoInput {

	private StatusPedido status;

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}
}
