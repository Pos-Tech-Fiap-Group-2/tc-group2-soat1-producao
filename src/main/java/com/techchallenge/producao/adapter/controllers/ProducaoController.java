package com.techchallenge.producao.adapter.controllers;

import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.core.domain.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.driver.model.input.PedidoInput;
import com.techchallenge.producao.adapter.mapper.api.ProducaoApiMapper;
import com.techchallenge.producao.core.domain.usecases.ProducaoUseCase;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class ProducaoController {
	
    @Autowired
    private ProducaoUseCase useCase;
    @Autowired
    private ProducaoApiMapper mapper;
    
	public void adicionarPedidoAFilaDeProducao(String id) {
		useCase.adicionarPedidoAFilaDeProducao(id);
	}

    public void atualizarStatusDePedidoEmProducao(PedidoInput input) throws IOException {
        useCase.atualizarStatusDePedidoEmProducao(mapper.toDomainObject(input));
    }

    public Collection<PedidoModel> consultarFilaDePedidos() {
        List<Pedido> pedidos = useCase.consultarFilaDePedidos();
        return mapper.toCollectionModel(pedidos);
    }

    public PedidoModel consultarStatusDePedidoEmProducao(String id) {
        Pedido pedido = useCase.consultarStatusDePedidoEmProducao(id);
        return mapper.toModel(pedido);
    }

    public Collection<PedidoModel> consultarHistoricoDeProducaoDePedido(String id) {
        List<Pedido> pedidos = useCase.consultarHistoricoDeProducaoDePedido(id);
        return mapper.toCollectionModel(pedidos);
    }
}