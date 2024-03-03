package com.techchallenge.producao.drivers.apis;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.adapter.driver.model.input.PedidoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;

@Api(tags = "Produção")
@RestController
@RequestMapping(value = "/api/producao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
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
			@Valid @PathVariable String id,
			@Valid @RequestBody PedidoInput input
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

	@ApiOperation(value = "Consultar Status de Pedido em Produção")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Status do pedido consultado com sucesso"),
			@ApiResponse(code = 404, message = "Pedido não encontrado")
	})
	@GetMapping(value = "/{id}/status")
	@ResponseStatus(HttpStatus.OK)
	public PedidoModel consultarStatusDePedidoEmProducao(@PathVariable String id) {
		return controller.consultarStatusDePedidoEmProducao(id);
	}

	@ApiOperation(value = "Consultar Histórico de Produção de um Pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Histórico de produção do pedido consultado com sucesso"),
			@ApiResponse(code = 404, message = "Pedido não encontrado")
	})
	@GetMapping(value = "/{id}/historico")
	@ResponseStatus(HttpStatus.OK)
	public Collection<PedidoModel> consultarHistoricoDeProducaoDePedido(@PathVariable String id) {
		return controller.consultarHistoricoDeProducaoDePedido(id);
	}
}