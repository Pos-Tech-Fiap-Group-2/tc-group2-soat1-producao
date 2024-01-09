package com.techchallenge.producao.core.domain.entities;

public class Pedido {

	private String pedidoId;
	private String status;

	public String getPedidoId() { return pedidoId; }

	public void setPedidoId(String pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getStatus() { return status; }

	public void setStatus(String status) {
		this.status = status;
	}
}
