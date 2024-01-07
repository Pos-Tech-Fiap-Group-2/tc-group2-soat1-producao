package com.techchallenge.producao.adapter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.driver.model.input.TemplateInput;
import com.techchallenge.producao.adapter.mapper.api.TemplateApiMapper;
import com.techchallenge.producao.core.domain.usecases.ProducaoUseCase;

@Component
public class ProducaoController {
	
    @Autowired
    private ProducaoUseCase useCase;
    @Autowired
    private TemplateApiMapper mapper;
    
	public void registrarMensagem(TemplateInput input) {
		useCase.registrarMensagem(mapper.toDomainObject(input));
	}
}