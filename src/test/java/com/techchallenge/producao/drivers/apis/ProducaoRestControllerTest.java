package com.techchallenge.producao.drivers.apis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.adapter.driver.model.input.PedidoInput;
import com.techchallenge.producao.adapter.mapper.api.ProducaoApiMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducaoRestControllerTest {

	@InjectMocks
	private ProducaoRestController rest;
	
    @Mock
    private ProducaoController controller;
    
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
    
	private Pedido createPedido(String id, String status, Date dataCriacao) {
		Pedido pedido = new Pedido();
		
		pedido.setPedidoId(id);
		pedido.setStatus(status);
		pedido.setDataCriacao(dataCriacao);
		
		return pedido;
	}
	
	private PedidoModel createPedidoModel(Pedido pedido) {
		PedidoModel model = new PedidoModel();
		
		model.setPedidoId(pedido.getPedidoId());
		model.setStatus(pedido.getStatus());
		model.setDataCriacao(pedido.getDataCriacao());
		
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
	
	private List<PedidoModel> createPedidoModels(List<Pedido> pedidos) {
		return pedidos.stream().map(p -> createPedidoModel(p))
				.collect(Collectors.toList());
	}
    
    @Test
    public void adicionarPedidoAFilaDeProducao() throws Exception {
    	doNothing().when(controller).adicionarPedidoAFilaDeProducao("1");
    	assertDoesNotThrow(() -> rest.adicionarPedidoAFilaDeProducao("1"));
    }
    
    @Test
	public void atualizarStatusDePedidoEmProducao() {
    	PedidoInput input = createPedidoInput("1", "EM_PREPARACAO");
    	
		doNothing().when(controller).atualizarStatusDePedidoEmProducao(input);
		assertDoesNotThrow(() -> rest.atualizarStatusDePedidoEmProducao("1", input));
	}
    
    @Test
	public void consultarFilaDePedidos() {
    	List<Pedido> pedidos = createPedidos();
    	List<PedidoModel> models = createPedidoModels(pedidos);
    	
    	when(mapper.toCollectionModel(pedidos)).thenReturn(models);
    	when(controller.consultarFilaDePedidos()).thenReturn(models);
    	
		assertTrue(rest.consultarFilaDePedidos().size() > 0);
	}

    @Test
	public void consultarStatusDePedidoEmProducao() {
    	Pedido pedido = createPedido("1", "RECEBIDO", Date.from(Instant.now()));
    	PedidoModel model = createPedidoModel(pedido);
    	
    	when(mapper.toModel(pedido)).thenReturn(model);
		when(controller.consultarStatusDePedidoEmProducao("1")).thenReturn(model);
		
		assertNotNull(rest.consultarStatusDePedidoEmProducao("1"));
	}

    @Test
	public void consultarHistoricoDeProducaoDePedido() {
    	List<Pedido> pedidos = createPedidos();
    	List<PedidoModel> models = createPedidoModels(pedidos);
    	
    	when(mapper.toCollectionModel(pedidos)).thenReturn(models);
		when(controller.consultarHistoricoDeProducaoDePedido("1")).thenReturn(models);
		
		assertTrue(rest.consultarHistoricoDeProducaoDePedido("1").size() > 0);
	}
}
