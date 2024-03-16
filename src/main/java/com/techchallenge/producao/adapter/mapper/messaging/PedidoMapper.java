package com.techchallenge.producao.adapter.mapper.messaging;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techchallenge.producao.core.domain.entities.messaging.PedidoAtualizacao;

@Component
public class PedidoMapper {

	public String toModel(PedidoAtualizacao content) {
		Gson gson = new GsonBuilder().create();
		
		return gson.toJson(content);
	}
}
