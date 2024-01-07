package com.techchallenge.producao.adapter.mapper.db;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.core.domain.entities.Template;
import com.techchallenge.producao.drivers.db.entities.TemplateEntity;

@Component
public class TemplateEntityMapper {

	@Autowired
	private ModelMapper mapper;
	
	public TemplateEntity toModel(Template tipo) {
		return mapper.map(tipo, TemplateEntity.class);
	}
	
	public List<TemplateEntity> toCollectionModel(Collection<Template> templates) {
		return templates.stream()
				.map(c -> mapper.map(c, TemplateEntity.class))
				.collect(Collectors.toList());
	}
}
