package com.techchallenge.producao.drivers.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.driver.model.input.TemplateInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produção")
@RestController
@RequestMapping(value = "/producao", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProducaoRestController {
	
    @Autowired
    private ProducaoController controller;
    
	@ApiOperation("Efetuar pagamento do pedido na plataforma")
	@ApiResponses({ 
			@ApiResponse(code = 201, message = "Pagamento registrado com sucesso"),
			@ApiResponse(code = 404, message = "Caso o pedido ou pagamento com o ID informado não exista")
			})
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void realizarPagamento(@RequestBody TemplateInput input) {
		controller.registrarMensagem(input);
	}
}