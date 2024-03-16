package com.techchallenge.producao.adapter.gateways.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
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

import com.techchallenge.producao.adapter.driver.messaging.producer.cliente.NotificacaoClienteProducer;
import com.techchallenge.producao.adapter.dto.ClienteDTO;
import com.techchallenge.producao.adapter.dto.PedidoDTO;
import com.techchallenge.producao.adapter.external.pedido.PedidoAPI;
import com.techchallenge.producao.adapter.mapper.business.ProducaoBusinessMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.core.domain.entities.messaging.Mensagem;
import com.techchallenge.producao.core.domain.entities.messaging.StatusPedido;
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
	
	@Mock
	PedidoAPI pedidoApi;
	
	@Mock
	NotificacaoClienteProducer clienteProducer;
	
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
			this.add(createPedidoEntity("1", StatusPedido.RECEBIDO.name(), Date.from(Instant.now())));
			this.add(createPedidoEntity("2", StatusPedido.RECEBIDO.name(), Date.from(Instant.now().plusSeconds(1L))));
			this.add(createPedidoEntity("3", StatusPedido.RECEBIDO.name(), Date.from(Instant.now().plusSeconds(1L))));
		}};

		return entities;
	}
	
	@SuppressWarnings("serial")
	private List<Pedido> createPedidos() {
		List<Pedido> pedidos = new ArrayList<>() {{
			this.add(createPedido("1", StatusPedido.RECEBIDO.name(), Date.from(Instant.now())));
			this.add(createPedido("2", StatusPedido.RECEBIDO.name(), Date.from(Instant.now().plusSeconds(1L))));
			this.add(createPedido("3", StatusPedido.RECEBIDO.name(), Date.from(Instant.now().plusSeconds(1L))));
		}};

		return pedidos;
	}
	
	
	@Test
	public void adicionarPedidoAFilaDeProducao() {
		PedidoEntity pedidoEntity = createPedidoEntity("1", StatusPedido.RECEBIDO.name());
		
		when(repository.save(pedidoEntity)).thenReturn(pedidoEntity);
		assertDoesNotThrow(() -> gateway.adicionarPedidoAFilaDeProducao("1"));
	}
	
	@Test
	public void atualizarStatusDePedidoEmProducao() {
		PedidoEntity pedidoEntity = createPedidoEntity("1", StatusPedido.PREPARACAO.name());
		Pedido pedido = createPedido("1", StatusPedido.PREPARACAO.name());
		PedidoDTO dto = new PedidoDTO();
		
		dto.setId(Long.valueOf(pedido.getPedidoId()));
		dto.setValor(new BigDecimal("25.99"));
		dto.setStatus(StatusPedido.PREPARACAO);
		dto.setDataSolicitacao(OffsetDateTime.now());
		dto.setCliente(new ClienteDTO());
		dto.getCliente().setEmail("email@teste.com.br");
		dto.getCliente().setId(1L);
		dto.getCliente().setNome("Teste");
		
		
		when(repository.findByPedidoId(pedido.getPedidoId())).thenReturn(pedidoEntity);
		when(repository.save(pedidoEntity)).thenReturn(pedidoEntity);
		when(pedidoApi.buscarDadosPedido(1L)).thenReturn(dto);
		doNothing().when(clienteProducer).enviar(any(Mensagem.class));
		
		assertDoesNotThrow(() -> gateway.atualizarStatusDePedidoEmProducao(pedido));
	}
	
	@Test
	public void atualizarStatusDePedidoEmProducao_null() {
		Pedido pedido = createPedido("1", StatusPedido.PREPARACAO.name());
		
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
		pedidoEntity.setStatus(StatusPedido.RECEBIDO.name());
		pedidoEntity.setDataCriacao(current);
		
		Pedido pedido = new Pedido();
		pedido.setPedidoId("1");
		pedido.setStatus(StatusPedido.RECEBIDO.name());
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
		entity1.setStatus(StatusPedido.RECEBIDO.name());
		
		PedidoEntity entity2 = new PedidoEntity();
		entity2.setDataCriacao(Date.from(Instant.now().plusSeconds(1L)));
		entity2.setPedidoId("1");
		entity2.setStatus(StatusPedido.RECEBIDO.name());
		
		PedidoEntity entity3 = new PedidoEntity();
		entity3.setDataCriacao(Date.from(Instant.now().plusSeconds(1L)));
		entity3.setPedidoId("1");
		entity3.setStatus(StatusPedido.RECEBIDO.name());
		
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
