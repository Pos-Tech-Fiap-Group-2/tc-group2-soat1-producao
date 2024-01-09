package com.techchallenge.producao.drivers.db.entities;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

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
}
