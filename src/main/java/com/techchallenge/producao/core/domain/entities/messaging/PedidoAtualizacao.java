package com.techchallenge.producao.core.domain.entities.messaging;

public class PedidoAtualizacao {

	private Long id;
	private StatusPedido status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}
}
