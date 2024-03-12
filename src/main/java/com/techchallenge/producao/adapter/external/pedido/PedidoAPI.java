package com.techchallenge.producao.adapter.external.pedido;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.techchallenge.producao.adapter.dto.PedidoDTO;

@FeignClient(name = "pedido-service", url = "${pedido.api.url}")
public interface PedidoAPI {

	
	@GetMapping(value = "/api/pedidos/{pedidoId}")
	public PedidoDTO buscarDadosPedido(@PathVariable Long pedidoId);
}
