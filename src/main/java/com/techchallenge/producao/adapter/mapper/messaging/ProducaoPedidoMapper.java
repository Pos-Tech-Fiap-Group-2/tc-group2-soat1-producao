package com.techchallenge.producao.adapter.mapper.messaging;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techchallenge.producao.core.domain.entities.Pedido;

@Component
public class ProducaoPedidoMapper {

	public Pedido toModel(String content) {
		Gson gson = new GsonBuilder().create();
		
		return gson.fromJson(content, Pedido.class);
	}
}
