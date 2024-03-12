package com.techchallenge.producao.core.domain.entities.messaging;

import java.time.OffsetDateTime;

public class Mensagem {

	private Cliente cliente;
	private OffsetDateTime timestamp;
	private String template;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "Mensagem [cliente=" + cliente + ", timestamp=" + timestamp + ", template=" + template + "]";
	}
}
