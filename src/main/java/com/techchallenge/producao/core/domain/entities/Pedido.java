package com.techchallenge.producao.core.domain.entities;

import java.util.Date;

public class Pedido {

	private String pedidoId;
	private String status;

	private Date dataCriacao;

	public String getPedidoId() { return pedidoId; }

	public void setPedidoId(String pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getStatus() { return status; }

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }
}
