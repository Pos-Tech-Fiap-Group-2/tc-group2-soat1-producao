package com.techchallenge.producao.adapter.gateways.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
import com.techchallenge.producao.adapter.mapper.business.ProducaoBusinessMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.drivers.db.entities.PedidoEntity;
import com.techchallenge.producao.drivers.db.repositories.ProducaoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducaoGatewayImplTest {
	
	@InjectMocks
	ProducaoGatewayImpl gateway;

	@Mock
	ProducaoRepository repository;

	@Mock
	ProducaoBusinessMapper mapper;
	
    @BeforeEach
    private void setup() {
    	MockitoAnnotations.openMocks(this);
    }
	
	private PedidoEntity createPedidoEntity(String id, String status) {
		return createPedidoEntity(id, status, Date.from(Instant.now()));
	}
	
	private PedidoEntity createPedidoEntity(String id, String status, Date dataCriacao) {
		PedidoEntity entity = new PedidoEntity();
		
		entity.setPedidoId(id);
		entity.setStatus(status);
		entity.setDataCriacao(dataCriacao);
		
		return entity;
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
	private List<PedidoEntity> createPedidoEntities() {
		List<PedidoEntity> entities = new ArrayList<>() {{
			this.add(createPedidoEntity("1", "RECEBIDO", Date.from(Instant.now())));
			this.add(createPedidoEntity("2", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
			this.add(createPedidoEntity("3", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
		}};

		return entities;
	}
	
	@SuppressWarnings("serial")
	private List<Pedido> createPedidos() {
		List<Pedido> pedidos = new ArrayList<>() {{
			this.add(createPedido("1", "RECEBIDO", Date.from(Instant.now())));
			this.add(createPedido("2", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
			this.add(createPedido("3", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
		}};

		return pedidos;
	}
	
	
	@Test
	public void adicionarPedidoAFilaDeProducao() {
		PedidoEntity pedidoEntity = createPedidoEntity("1", "RECEBIDO");
		
		when(repository.save(pedidoEntity)).thenReturn(pedidoEntity);
		assertDoesNotThrow(() -> gateway.adicionarPedidoAFilaDeProducao("1"));
	}
	
	@Test
	public void atualizarStatusDePedidoEmProducao() {
		PedidoEntity pedidoEntity = createPedidoEntity("1", "EM_PREPARACAO");
		Pedido pedido = createPedido("1", "EM_PREPARACAO");
		
		when(repository.findByPedidoId(pedido.getPedidoId())).thenReturn(pedidoEntity);
		when(repository.save(pedidoEntity)).thenReturn(pedidoEntity);
		
		assertDoesNotThrow(() -> gateway.atualizarStatusDePedidoEmProducao(pedido));
	}
	
	@Test
	public void atualizarStatusDePedidoEmProducao_null() {
		Pedido pedido = createPedido("1", "EM_PREPARACAO");
		
		when(repository.findByPedidoId(pedido.getPedidoId())).thenReturn(null);
		assertThrows(RuntimeException.class, () -> gateway.atualizarStatusDePedidoEmProducao(pedido));
	}
	
	@Test
	public void consultarFilaDePedidos() {
		List<PedidoEntity> entities = this.createPedidoEntities();
		
		Collections.sort(entities, (PedidoEntity o1, PedidoEntity o2) -> {
			return o1.getDataCriacao().compareTo(o2.getDataCriacao()) * -1;
		});
		
		when(repository.findAllGroupByPedidoIdOrderByDataCriacao()).thenReturn(entities);
		when(mapper.toCollectionModel(entities)).thenReturn(createPedidos());
		
		assertTrue(gateway.consultarFilaDePedidos().size() > 0);
	}
	
	@Test
	public void consultarStatusDePedidoEmProducao() {
		Date current = Date.from(Instant.now());
		
		PedidoEntity pedidoEntity = new PedidoEntity();
		pedidoEntity.setPedidoId("1");
		pedidoEntity.setStatus("RECEBIDO");
		pedidoEntity.setDataCriacao(current);
		
		Pedido pedido = new Pedido();
		pedido.setPedidoId("1");
		pedido.setStatus("RECEBIDO");
		pedido.setDataCriacao(current);
		
		when(repository.findByPedidoId("1")).thenReturn(pedidoEntity);
		when(mapper.toModel(pedidoEntity)).thenReturn(pedido);
		
		assertEquals(gateway.consultarStatusDePedidoEmProducao("1"), pedido);
	}
	
	@Test
	public void consultarHistoricoDeProducaoDePedido() {
		List<PedidoEntity> entities = new ArrayList<>();
		
		PedidoEntity entity1 = new PedidoEntity();
		entity1.setDataCriacao(Date.from(Instant.now()));
		entity1.setPedidoId("1");
		entity1.setStatus("RECEBIDO");
		
		PedidoEntity entity2 = new PedidoEntity();
		entity2.setDataCriacao(Date.from(Instant.now().plusSeconds(1L)));
		entity2.setPedidoId("1");
		entity2.setStatus("RECEBIDO");
		
		PedidoEntity entity3 = new PedidoEntity();
		entity3.setDataCriacao(Date.from(Instant.now().plusSeconds(1L)));
		entity3.setPedidoId("1");
		entity3.setStatus("RECEBIDO");
		
		entities.add(entity1);
		entities.add(entity2);
		entities.add(entity3);
		
		Collections.sort(entities, (PedidoEntity o1, PedidoEntity o2) -> {
			return o1.getDataCriacao().compareTo(o2.getDataCriacao()) * -1;
		});
		
		when(repository.findAllByPedidoIdOrderByDataCriacao("1")).thenReturn(entities);
		when(mapper.toCollectionModel(entities)).thenReturn(createPedidos());
		
		assertTrue(gateway.consultarHistoricoDeProducaoDePedido("1").size() > 0);
	}
}
