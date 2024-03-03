package com.techchallenge.producao.drivers.apis;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.driver.exceptionhandler.ApiExceptionHandler;
import com.techchallenge.producao.adapter.driver.model.PedidoModel;
import com.techchallenge.producao.core.domain.entities.Pedido;
import com.techchallenge.producao.util.ResourceUtil;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProducaoRestControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ProducaoRestController rest;

	@Mock
	private ProducaoController controller;
	
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
	
	private ApiExceptionHandler createHandler() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		// Geração de instância de MessageSource mock para injeção no Handler via Reflection API.
		MessageSource messageSourceTest = new MessageSource() {

			public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
				return String.format("Message mock: Code: %s - DefaultMessage: %s : args %s", new Object[] {code, defaultMessage, args});
			}

			public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
				return String.format("Message mock: Code: %s : args %s", new Object[] {code, args});
			}

			public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
				return "Message mock";
			}
		};

		// Como não estamos subindo o contexto do Spring boot, o MessageSource fica com referência nula
		// por estarmos instanciando diretamente o Handler.
		ApiExceptionHandler handler = new ApiExceptionHandler();
		
		// Injeção da referência do messageSource via reflection API.
		Class<? extends ApiExceptionHandler> clazz = handler.getClass();
		
		Field messageSourceField = clazz.getDeclaredField("messageSource");
		messageSourceField.setAccessible(Boolean.TRUE);
		
		messageSourceField.set(handler, messageSourceTest);
		
		return handler;
	}
	
	@BeforeEach
	private void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MockitoAnnotations.openMocks(this);
		
		// Handler mock.
		ApiExceptionHandler handler = createHandler();

		mockMvc = MockMvcBuilders.standaloneSetup(rest).setControllerAdvice(handler)
				.addFilter((request, response, chain) -> {
					response.setCharacterEncoding("UTF-8");
					chain.doFilter(request, response);
				}, "/*").build();
	}

	@Test
	public void dadoIdDoPedido_quandoAdicionarAFila_entaoStatus200() throws Exception {

		mockMvc.perform(post("/api/producao/1/adicionar").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void dadoIdDoPedido_quandoAtualizarStatusDoPedido_entaoStatus200() throws Exception {

		String content = ResourceUtil.getContentFromResource(
				"/json/correto/pedido-input.json");

		mockMvc.perform(put("/api/producao/1/status").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().isOk());
	}
	
	@Test
	public void dadoFilaDePedidos_quandoListarPedidos_entaoStatus200() throws Exception {

		when(controller.consultarFilaDePedidos()).thenReturn(createPedidoModels(createPedidos()));
		
		MvcResult result = mockMvc.perform(get("/api/producao/fila")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		List<?> lista = new Gson().fromJson(content, List.class);
		assertTrue(lista.size() > 0);
	}
	
	@Test
	public void dadoIdDoPedido_quandoConsultarStatusDoPedido_entaoStatus200() throws Exception {

		PedidoModel model = createPedidoModel(createPedido("1", "RECEBIDO", Date.from(Instant.now())));
		
		when(controller.consultarStatusDePedidoEmProducao("1")).thenReturn(model);
		
		mockMvc.perform(get("/api/producao/1/status")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$.pedidoId").value(model.getPedidoId()))
	      .andExpect(jsonPath("$.status").value(model.getStatus()))
	      .andExpect(jsonPath("$.dataCriacao").exists());
		
		verify(controller, times(1)).consultarStatusDePedidoEmProducao(any(String.class));
	}
	
	@Test
	public void dadoPedidos_quandoConsultarHistoricoDoPedido_entaoStatus200() throws Exception {

		when(controller.consultarHistoricoDeProducaoDePedido("1")).thenReturn(createPedidoModels(createPedidos()));
		
		MvcResult result = mockMvc.perform(get("/api/producao/1/historico")
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

		mockMvc.perform(put("/api/producao/1/status").contentType(MediaType.APPLICATION_JSON).content(content))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void dadoStatus_quandoConsultarComPayloadIncorreto_entaoStatus400() throws Exception {
		
		mockMvc.perform(put("/api/producao/1/status").contentType(MediaType.APPLICATION_JSON).content("{\"pedidoId\": \"1\"\"status\" : \"RECEBIDO\"}"))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void dadoStatus_quandoConsultarComMediaTypeIncorreto_entaoStatus400() throws Exception {
		
		mockMvc.perform(put("/api/producao/1/status").contentType(MediaType.APPLICATION_XML).content(""))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void dadoIdDoPedido_quandoConsultarStatus_entaoStatus500() throws Exception {
		
		when(controller.consultarStatusDePedidoEmProducao("1"))
			.thenThrow(new RuntimeException("Erro inesperado"));
	
		mockMvc.perform(get("/api/producao/1/status")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().is5xxServerError());
	}
}
