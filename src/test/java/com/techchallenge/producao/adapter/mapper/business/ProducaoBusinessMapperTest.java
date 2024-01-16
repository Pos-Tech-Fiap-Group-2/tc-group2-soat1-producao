package com.techchallenge.producao.adapter.mapper.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.drivers.db.entities.PedidoEntity;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducaoBusinessMapperTest {

	@InjectMocks
	private ProducaoBusinessMapper mapper;
	
	@Mock
	private ModelMapper modelMapper;
	
    @BeforeEach
    private void setup() {
    	MockitoAnnotations.openMocks(this);
    }
    
	@SuppressWarnings("serial")
	private List<PedidoEntity> createPedidoEntities() {
		List<PedidoEntity> entities = new ArrayList<>() {{
			this.add(createPedidoEntity(createPedido("1", "RECEBIDO", Date.from(Instant.now()))));
			this.add(createPedidoEntity(createPedido("2", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L)))));
			this.add(createPedidoEntity(createPedido("3", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L)))));
		}};

		return entities;
	}
	
	private PedidoEntity createPedidoEntity(Pedido pedido) {
		PedidoEntity entity = new PedidoEntity();
		
		entity.setPedidoId(pedido.getPedidoId());
		entity.setStatus(pedido.getStatus());
		entity.setDataCriacao(pedido.getDataCriacao());
		
		return entity;
	}
	
	private Pedido createPedido(String id, String status, Date dataCriacao) {
		Pedido pedido = new Pedido();
		
		pedido.setPedidoId(id);
		pedido.setStatus(status);
		pedido.setDataCriacao(dataCriacao);
		
		return pedido;
	}
	
    @Test
    public void toCollectionModel() {
    	Pedido pedido = createPedido("1", "RECEBIDO", Date.from(Instant.now()));
    	PedidoEntity entity = createPedidoEntity(pedido);
    	List<PedidoEntity> entities = createPedidoEntities();
    	
    	when(modelMapper.map(entity, Pedido.class)).thenReturn(pedido);
    	assertTrue(mapper.toCollectionModel(entities).size() > 0);
    }

    @Test
    public void toModel() {
    	Pedido pedido = createPedido("1", "RECEBIDO", Date.from(Instant.now()));
    	PedidoEntity entity = createPedidoEntity(pedido);
    	
    	when(modelMapper.map(entity, Pedido.class)).thenReturn(pedido);
        assertNotNull(mapper.toModel(entity));
    }
}
