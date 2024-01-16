package com.techchallenge.producao.core.domain.usecases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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

import com.techchallenge.producao.adapter.gateways.ProducaoGateway;
import com.techchallenge.producao.core.domain.entities.Pedido;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducaoUseCaseTest {
	
	@InjectMocks
	ProducaoUseCase useCase;
	
	@Mock
	ProducaoGateway gateway;
	
    @BeforeEach
    private void setup() {
    	MockitoAnnotations.openMocks(this);
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
	
	@SuppressWarnings("serial")
	private List<Pedido> createPedidos() {
		List<Pedido> entities = new ArrayList<>() {{
			this.add(createPedido("1", "RECEBIDO", Date.from(Instant.now())));
			this.add(createPedido("2", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
			this.add(createPedido("3", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
		}};

		return entities;
	}
	
	@Test
	public void adicionarPedidoAFilaDeProducao() {
		doNothing().when(gateway).adicionarPedidoAFilaDeProducao("1");
		assertDoesNotThrow(() -> useCase.adicionarPedidoAFilaDeProducao("1"));
	}

	@Test
	public void atualizarStatusDePedidoEmProducao() {
		Pedido pedido = createPedido("1", "RECEBIDO");
		
		doNothing().when(gateway).atualizarStatusDePedidoEmProducao(pedido);
		assertDoesNotThrow(() -> useCase.atualizarStatusDePedidoEmProducao(pedido));
	}

	@Test
	public void consultarFilaDePedidos() {
		List<Pedido> pedidos = createPedidos();
		
		when(gateway.consultarFilaDePedidos()).thenReturn(pedidos);
		assertTrue(useCase.consultarFilaDePedidos().size() > 0);
	}

	@Test
    public void consultarStatusDePedidoEmProducao() {
		Pedido pedido = createPedido("1", "RECEBIDO");
		
		when(gateway.consultarStatusDePedidoEmProducao(pedido.getPedidoId())).thenReturn(pedido);
		assertEquals(useCase.consultarStatusDePedidoEmProducao(pedido.getPedidoId()), pedido);
    }

	@Test
	public void consultarHistoricoDeProducaoDePedido() {
		List<Pedido> pedidos = createPedidos();
		when(gateway.consultarHistoricoDeProducaoDePedido("1")).thenReturn(pedidos);
		
		assertTrue(useCase.consultarHistoricoDeProducaoDePedido("1").size() > 0);
	}
}
