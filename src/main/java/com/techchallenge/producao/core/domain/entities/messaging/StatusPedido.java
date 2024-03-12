package com.techchallenge.producao.core.domain.entities.messaging;

import java.util.Optional;
import java.util.stream.Stream;

public enum StatusPedido {

	RECEBIDO("received"),
	CANCELADO("canceled"),
	EM_PREPARACAO("preparing"),
	PRONTO("done");
	
	private StatusPedido(String status) {
		this.status = status;
	}
	
	private String status;
	
	public String getStatus() {
		return this.status;
	}
	
	public static StatusPedido getStatusPedidoByName(String name) {
		Optional<StatusPedido> optional = Stream.of(StatusPedido.values()).filter(s -> s.name().equals(name)).findFirst();
		return optional.orElse(null);
	}
}
