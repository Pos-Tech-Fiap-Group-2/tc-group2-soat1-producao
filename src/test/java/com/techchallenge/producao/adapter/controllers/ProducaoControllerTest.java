package com.techchallenge.producao.adapter.controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.adapter.driver.model.input.PedidoInput;
import com.techchallenge.producao.adapter.mapper.api.ProducaoApiMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.core.domain.usecases.ProducaoUseCase;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducaoControllerTest {

	@InjectMocks
	private ProducaoController controller;

    @Mock
    private ProducaoUseCase useCase;
    
    @Mock
    private ProducaoApiMapper mapper;
    
    @BeforeEach
    private void setup() {
    	MockitoAnnotations.openMocks(this);
    }
    
    private PedidoInput createPedidoInput(String id, String status) {
    	PedidoInput input = new PedidoInput();
    	input.setPedidoId(id);
    	input.setStatus(status);
    	
    	return input;
    }
    
	private Pedido createPedido(String id, String status) {
		return createPedido(id, status, Date.from(Instant.now()));
	}
	
	private Pedido createPedido(String id, String status, Date dataCriacao) {
		Pedido pedido = new Pedido();
		
		pedido.setPedidoId(id);
		pedido.setStatus(status);
		pedido.setDataCriacao(dataCriacao);
		
		return pedido;
	}
	
	private PedidoModel createPedidoModel(Pedido pedido) {
		PedidoModel model = new PedidoModel();
		model.setDataCriacao(pedido.getDataCriacao());
		model.setPedidoId(pedido.getPedidoId());
		model.setStatus(pedido.getStatus());
		
		return model;
	}
	
	@SuppressWarnings("serial")
	private List<Pedido> createPedidos() {
		List<Pedido> entities = new ArrayList<>() {{
			this.add(createPedido("1", "RECEBIDO", Date.from(Instant.now())));
			this.add(createPedido("2", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
			this.add(createPedido("3", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
		}};

		return entities;
	}
	
	@SuppressWarnings("serial")
	private List<PedidoModel> createPedidosModel() {
		List<PedidoModel> models = new ArrayList<>() {{
			this.add(createPedidoModel(createPedido("1", "RECEBIDO", Date.from(Instant.now()))));
			this.add(createPedidoModel(createPedido("2", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L)))));
			this.add(createPedidoModel(createPedido("3", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L)))));
		}};

		return models;
	}
    
    @Test
	public void adicionarPedidoAFilaDeProducao() {
		controller.adicionarPedidoAFilaDeProducao("1");
	}

    @Test
    public void atualizarStatusDePedidoEmProducao() {
    	PedidoInput input = createPedidoInput("1", "RECEBIDO");
        controller.atualizarStatusDePedidoEmProducao(input);
    }

    @Test
    public void consultarFilaDePedidos() {
    	List<Pedido> pedidos = createPedidos();
    	List<PedidoModel> pedidosModel = createPedidosModel();
    	
    	when(useCase.consultarFilaDePedidos()).thenReturn(pedidos);
    	when(mapper.toCollectionModel(pedidos)).thenReturn(pedidosModel);
    	
    	assertTrue(controller.consultarFilaDePedidos().size() > 0);
    }

    @Test
    public void consultarStatusDePedidoEmProducao() {
    	Pedido pedido = createPedido("1", "RECEBIDO");
    	PedidoModel model = createPedidoModel(pedido);
    	
    	when(useCase.consultarStatusDePedidoEmProducao(pedido.getPedidoId())).thenReturn(pedido);
    	when(mapper.toModel(pedido)).thenReturn(model);
    	
    	assertEquals(controller.consultarStatusDePedidoEmProducao("1"), model);
    }

    @Test
    public void consultarHistoricoDeProducaoDePedido() {
    	List<Pedido> pedidos = createPedidos();
    	List<PedidoModel> pedidosModel = createPedidosModel();
    	
    	when(useCase.consultarHistoricoDeProducaoDePedido("1")).thenReturn(pedidos);
    	when(mapper.toCollectionModel(pedidos)).thenReturn(pedidosModel);
    	
    	assertTrue(controller.consultarHistoricoDeProducaoDePedido("1").size() > 0);
    }
}
