package com.techchallenge.producao.adapter.external.pedido;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.techchallenge.producao.adapter.dto.PedidoDTO;
import com.techchallenge.producao.adapter.external.pedido.model.StatusPedidoInput;

@FeignClient(name = "pedido-service", url = "${pedido.api.url}")
public interface PedidoAPI {

	
	@GetMapping(value = "/api/pedidos/{pedidoId}")
	public PedidoDTO buscarDadosPedido(@PathVariable Long pedidoId);
	
	@PutMapping(value = "/api/pedidos/{pedidoId}/status")
	public void atualizarStatusPedido(@PathVariable Long pedidoId, @RequestBody StatusPedidoInput input);
}
