package com.techchallenge.producao.adapter.mapper.business;

import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.drivers.db.entities.PedidoEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProducaoBusinessMapper {

    @Autowired
    private ModelMapper mapper;

    public List<Pedido> toCollectionModel(List<PedidoEntity> pedidos) {
        return pedidos.stream()
                .map(c -> mapper.map(c, Pedido.class))
                .collect(Collectors.toList());
    }
}
