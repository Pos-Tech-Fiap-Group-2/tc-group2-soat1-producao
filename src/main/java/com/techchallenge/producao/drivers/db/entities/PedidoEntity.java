package com.techchallenge.producao.drivers.db.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "pedidos")
public class PedidoEntity {

	@Id
	private String id;

	private String pedidoId;

	private String status;

	@CreatedDate
	private Date dataCriacao;

	public String getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) { this.status = status; }
	public Date getDataCriacao() { return dataCriacao; }
	public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

	@Override
	public int hashCode() {
		return Objects.hash(dataCriacao, id, pedidoId, status);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoEntity other = (PedidoEntity) obj;
		return Objects.equals(dataCriacao, other.dataCriacao) && Objects.equals(id, other.id)
				&& Objects.equals(pedidoId, other.pedidoId) && Objects.equals(status, other.status);
	}
}
