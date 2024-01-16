package com.techchallenge.producao.adapter.driver.model;

import java.util.Date;
import java.util.Objects;

public class PedidoModel {

	private String pedidoId;
	private String status;
	private Date dataCriacao;

	public String getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(String pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCriacao, pedidoId, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoModel other = (PedidoModel) obj;
		return Objects.equals(dataCriacao, other.dataCriacao) && Objects.equals(pedidoId, other.pedidoId)
				&& Objects.equals(status, other.status);
	}
}
