package com.techchallenge.producao.adapter.driver.messaging.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.mapper.messaging.ProducaoPedidoMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class ProducaoPedidoConsumer {

	@Autowired
	private ProducaoPedidoMapper mapper;
	
    @Autowired
    private ProducaoController controller;
	
    @SqsListener(value = "${cloud.aws.queue.name.producao-pedido-inclusao}", id = "producao-pedido")
    public void adicionarPedido(String mensagem) {
    	System.out.println(mensagem);
    	Pedido pedido = mapper.toModel(mensagem);
    	controller.adicionarPedidoAFilaDeProducao(pedido.getPedidoId());
    }
}
