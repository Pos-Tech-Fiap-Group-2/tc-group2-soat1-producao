package com.techchallenge.producao.adapter.driver.model.input;

import jakarta.validation.constraints.NotEmpty;

public class PedidoInput {

	@NotEmpty
	private String pedidoId;
	@NotEmpty
	private String status;

	public String getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
