package com.techchallenge.producao.drivers.apis;

import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.driver.model.input.PedidoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;

@Api(tags = "Produção")
@RestController
@RequestMapping(value = "/producao", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducaoRestController {
	
    @Autowired
    private ProducaoController controller;

	@ApiOperation(value = "Adicionar pedido à fila de produção")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Pedido adicionado à fila de produção com sucesso"),
			@ApiResponse(code = 404, message = "Pedido não encontrado"),
	})
	@PostMapping(value = "/{id}/adicionar")
	@ResponseStatus(HttpStatus.CREATED)
	public void adicionarPedidoAFilaDeProducao(@PathVariable String id) {
		controller.adicionarPedidoAFilaDeProducao(id);
	}

	@ApiOperation(value = "Atualizar Status de Pedido em Produção")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Status do pedido atualizado com sucesso"),
			@ApiResponse(code = 404, message = "Pedido não encontrado")
	})
	@PutMapping(value = "/{id}/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatusDePedidoEmProducao(
			@PathVariable String id,
			@RequestBody PedidoInput input
	) {
		input.setPedidoId(id);
		controller.atualizarStatusDePedidoEmProducao(input);
	}

	@ApiOperation(value = "Consultar a Fila de Pedidos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Fila de Pedidos consultada com sucesso")
	})
	@GetMapping(value = "/fila")
	@ResponseStatus(HttpStatus.OK)
	public Collection<PedidoModel> consultarFilaDePedidos() {
		return controller.consultarFilaDePedidos();
	}
}