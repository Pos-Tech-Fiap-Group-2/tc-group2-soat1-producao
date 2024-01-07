package com.techchallenge.producao.adapter.gateways.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.gateways.ProducaoGateway;
import com.techchallenge.producao.core.domain.entities.Template;
import com.techchallenge.producao.drivers.db.entities.TemplateEntity;
import com.techchallenge.producao.drivers.db.repositories.TemplateRepository;

@Component
public class ProducaoGatewayImpl implements ProducaoGateway {
	
	@Autowired
	private TemplateRepository templateRepository;
	
	@Override
	public void registrarMensagem(Template template) {
		
		TemplateEntity entity = new TemplateEntity();
		entity.setMensagem(template.getMensagem());
		
		templateRepository.save(entity);
		
	}
}
