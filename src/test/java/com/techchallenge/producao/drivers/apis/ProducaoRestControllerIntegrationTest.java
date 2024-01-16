package com.techchallenge.producao.drivers.apis;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.util.ResourceUtil;


@RunWith(SpringRunner.class)
@WebMvcTest(ProducaoRestController.class)
public class ProducaoRestControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProducaoController controller;
	
	@MockBean(name = "mongoMappingContext")
	private MongoMappingContext mongoMappingContext;
	
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
		List<Pedido> entities = new ArrayList<>() {
			{
				this.add(createPedido("1", "RECEBIDO", Date.from(Instant.now())));
				this.add(createPedido("2", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
				this.add(createPedido("3", "RECEBIDO", Date.from(Instant.now().plusSeconds(1L))));
			}
		};

		return entities;
	}

	private List<PedidoModel> createPedidoModels(List<Pedido> pedidos) {
		return pedidos.stream().map(p -> createPedidoModel(p)).collect(Collectors.toList());
	}

	@Test
	public void dadoIdDoPedido_quandoAdicionarAFila_entaoStatus200() throws Exception {

		mvc.perform(post("/producao/1/adicionar").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void dadoIdDoPedido_quandoAtualizarStatusDoPedido_entaoStatus200() throws Exception {

		String content = ResourceUtil.getContentFromResource(
				"/json/correto/pedido-input.json");

		mvc.perform(put("/producao/1/status").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}
	
	@Test
	public void dadoFilaDePedidos_quandoListarPedidos_entaoStatus200() throws Exception {

		when(controller.consultarFilaDePedidos()).thenReturn(createPedidoModels(createPedidos()));
		
		MvcResult result = mvc.perform(get("/producao/fila")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		List<?> lista = new Gson().fromJson(content, List.class);
		assertTrue(lista.size() > 0);
	}
	
	@Test
	public void dadoIdDoPedido_quandoConsultarStatusDoPedido_entaoStatus200() throws Exception {

		when(controller.consultarStatusDePedidoEmProducao("1"))
			.thenReturn(createPedidoModel(createPedido("1", "RECEBIDO", Date.from(Instant.now()))));
		
		MvcResult result = mvc.perform(get("/producao/1/status")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		PedidoModel pedidoModel = new Gson().fromJson(content, PedidoModel.class);
		assertNotNull(pedidoModel);
	}
	
	@Test
	public void dadoPedidos_quandoConsultarHistoricoDoPedido_entaoStatus200() throws Exception {

		when(controller.consultarHistoricoDeProducaoDePedido("1")).thenReturn(createPedidoModels(createPedidos()));
		
		MvcResult result = mvc.perform(get("/producao/1/historico")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		List<?> lista = new Gson().fromJson(content, List.class);
		assertTrue(lista.size() > 0);
	}
	
	@Test
	public void dadoPedido_quandoConsultarStatusSemPedidoId_entaoStatus400() throws Exception {
		
		String content = ResourceUtil.getContentFromResource(
				"/json/incorreto/pedido-input-sem-pedidoId.json");

		mvc.perform(put("/producao/1/status").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void dadoStatus_quandoConsultarComPayloadIncorreto_entaoStatus400() throws Exception {
		
		mvc.perform(put("/producao/1/status").contentType(MediaType.APPLICATION_JSON).content("{\"pedidoId\": \"1\"\"status\" : \"RECEBIDO\"}"))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void dadoStatus_quandoConsultarComMediaTypeIncorreto_entaoStatus400() throws Exception {
		
		mvc.perform(put("/producao/1/status").contentType(MediaType.APPLICATION_XML).content(""))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void dadoIdDoPedido_quandoConsultarStatus_entaoStatus500() throws Exception {
		
		when(controller.consultarStatusDePedidoEmProducao("1"))
			.thenThrow(new RuntimeException("Erro inesperado"));
	
		mvc.perform(get("/producao/1/status")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().is5xxServerError());
	}
}
