package com.techchallenge.producao.adapter.mapper.api;

import java.util.List;
import java.util.stream.Collectors;

import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.core.domain.entities.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.driver.model.input.PedidoInput;

@Component
public class ProducaoApiMapper {

    @Autowired
    private ModelMapper mapper;

    public Pedido toDomainObject(PedidoInput input) {
        return mapper.map(input, Pedido.class);
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(c -> mapper.map(c, PedidoModel.class))
                .collect(Collectors.toList());
    }

    public PedidoModel toModel(Pedido pedido) {
        return mapper.map(pedido, PedidoModel.class);
    }
}
