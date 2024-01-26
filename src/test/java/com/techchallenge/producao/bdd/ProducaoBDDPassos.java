package com.techchallenge.producao.bdd;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.techchallenge.producao.util.ResourceUtil;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

public class ProducaoBDDPassos {

	private Response response;

	private static String ENDPOINT_PRODUCAO;
	
	static {
		Properties prop = new Properties();
		InputStream is = ProducaoBDDPassos.class.getResourceAsStream("/bdd-config.properties");
		
		try {
			prop.load(is);
			ENDPOINT_PRODUCAO = prop.getProperty("bdd.endpoint.url");
			
		} catch (IOException e) {
			
		}
	}

	private Response adicionarPedido(String id) {
		return given().contentType(MediaType.APPLICATION_JSON_VALUE).pathParam("id", id).when()
				.post(ENDPOINT_PRODUCAO + "/{id}/adicionar");
	}
	
	private Response consultarPedido(String id) {
		return given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.pathParam("id", id)
				.when()
				.get(ENDPOINT_PRODUCAO + "/{id}/status");
	}

	@Quando("Adicionar pedido à fila de produção")
	public void adicionarPedidoAFila() {
		response = adicionarPedido("1");
	}

	@Então("o pedido é adicionado com sucesso")
	public void pedidoRegistradoComSucesso() {
		response.then().statusCode(HttpStatus.CREATED.value());
	}

	@Dado("que um pedido já registrado")
	public void pedidoJaAdicionadoAFila() {
		response = consultarPedido("1");
		response.then().statusCode(HttpStatus.OK.value());
	}

	@Quando("requisitar a atualização do pedido")
	public void atualizarPedidoNaFila() {
		String content = ResourceUtil.getContentFromResource(
				"/json/correto/pedido-input-bdd.json");
		
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.pathParam("id", "1")
				.body(content)
				.when()
				.put(ENDPOINT_PRODUCAO + "/{id}/status");
	}

	@Então("o pedido é atualizado com sucesso")
	public void pedidoAtualizadoComSucesso() {
		response.then().statusCode(HttpStatus.OK.value());
	}
	
	@Dado("que pedidos estejam na fila de produção")
	public void pedidosNaFilaDeProducao() {
		response = consultarPedido("1");
		response.then().statusCode(HttpStatus.OK.value());
	}

	@Quando("requisitar a lista de pedidos na fila")
	public void requisitarListaDePedidosNaFila() {
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
				.get(ENDPOINT_PRODUCAO + "/fila");
	}

	@Então("os pedidos são exibidos com sucesso")
	public void pedidosExibidosComSucesso() {
		response.then().statusCode(HttpStatus.OK.value());
	}
	
	@Dado("que um pedido esteja na fila de produção")
	public void pedidoNaFilaDeProducao() {
		response = consultarPedido("1");
		response.then().statusCode(HttpStatus.OK.value());
	}

	@Quando("requisitar a consulta do pedido na fila de produção")
	public void requisitarConsultaPedidoNaFilaDeProducao() {
		response = consultarPedido("1");
	}

	@Então("o pedido seja exibido com sucesso")
	public void pedidoExibidoComSucesso() {
		response.then().statusCode(HttpStatus.OK.value());
	}
	
	@Quando("requisitar a consulta do histórico do pedido na fila de produção")
	public void requisitarConsultaDoHistoricoPedidoNaFilaDeProducao() {
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.pathParam("id", "1")
				.when()
				.get(ENDPOINT_PRODUCAO + "/{id}/historico");
	}

	@Então("a lista de histórico do pedido seja exibida com sucesso")
	public void listaDeHistoricoDoPedidoExibidaComSucesso() {
		response.then().statusCode(HttpStatus.OK.value());
	}
}
