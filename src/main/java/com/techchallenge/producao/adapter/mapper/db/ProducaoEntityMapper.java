package com.techchallenge.producao.adapter.mapper.db;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.techchallenge.producao.core.domain.entities.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.drivers.db.entities.PedidoEntity;

@Component
public class ProducaoEntityMapper {

	@Autowired
	private ModelMapper mapper;
	
	public PedidoEntity toModel(Pedido tipo) {
		return mapper.map(tipo, PedidoEntity.class);
	}
	
	public List<PedidoEntity> toCollectionModel(Collection<Pedido> pedido) {
		return pedido.stream()
				.map(c -> mapper.map(c, PedidoEntity.class))
				.collect(Collectors.toList());
	}
}
