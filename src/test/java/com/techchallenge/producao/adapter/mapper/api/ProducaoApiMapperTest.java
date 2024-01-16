package com.techchallenge.producao.adapter.mapper.api;

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

import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.adapter.driver.model.input.PedidoInput;
import com.techchallenge.producao.core.domain.entities.Pedido;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducaoApiMapperTest {

	@InjectMocks
	private ProducaoApiMapper mapper;
	
	@Mock
	private ModelMapper modelMapper;
	
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
	
    @Test
    public void toDomainObject() {
    	PedidoInput input = createPedidoInput("1", "RECEBIDO");
    	Pedido pedido = createPedido("1", "RECEBIDO", Date.from(Instant.now()));
    	
    	when(modelMapper.map(input, Pedido.class)).thenReturn(pedido);
        assertNotNull(mapper.toDomainObject(input));
    }

    @Test
    public void toCollectionModel() {
    	Pedido pedido = createPedido("1", "RECEBIDO", Date.from(Instant.now()));
    	PedidoModel model = createPedidoModel(pedido);
    	List<Pedido> pedidos = createPedidos();
    	
    	when(modelMapper.map(pedido, PedidoModel.class)).thenReturn(model);
    	assertTrue(mapper.toCollectionModel(pedidos).size() > 0);
    }

    @Test
    public void toModel() {
    	Pedido pedido = createPedido("1", "RECEBIDO", Date.from(Instant.now()));
    	PedidoModel model = createPedidoModel(pedido);
    	
    	when(modelMapper.map(pedido, PedidoModel.class)).thenReturn(model);
        assertNotNull(mapper.toModel(pedido));
    }
}
