package com.techchallenge.producao.core.domain.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techchallenge.producao.adapter.gateways.ProducaoGateway;
import com.techchallenge.producao.core.domain.entities.Template;

@Service
public class ProducaoUseCase {
	
	@Autowired
	private ProducaoGateway gateway;
	
	public void registrarMensagem(Template template) {
		this.gateway.registrarMensagem(template);
	}
}