package com.techchallenge.producao.adapter.mapper.api;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.driver.model.TemplateModel;
import com.techchallenge.producao.adapter.driver.model.input.TemplateInput;
import com.techchallenge.producao.core.domain.entities.Template;

@Component
public class TemplateApiMapper {

    @Autowired
    private ModelMapper mapper;

    public Template toDomainObject(TemplateInput input) {
        return mapper.map(input, Template.class);
    }

    public Collection<TemplateModel> toCollectionModel(Collection<Template> pagamentos) {
        return pagamentos.stream()
                .map(c -> mapper.map(c, TemplateModel.class))
                .collect(Collectors.toList());
    }

}
