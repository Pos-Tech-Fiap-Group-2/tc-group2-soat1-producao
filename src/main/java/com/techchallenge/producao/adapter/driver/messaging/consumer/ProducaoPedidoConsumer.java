package com.techchallenge.producao.adapter.driver.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.controllers.ProducaoController;
import com.techchallenge.producao.adapter.mapper.messaging.ProducaoPedidoMapper;
import com.techchallenge.producao.core.domain.entities.Pedido;

@Component
public class ProducaoPedidoConsumer {

	@Autowired
	private ProducaoPedidoMapper mapper;
	
    @Autowired
    private ProducaoController controller;
    
    private Logger logger = LoggerFactory.getLogger(ProducaoPedidoConsumer.class);
	
    @RabbitListener(queues = {"${queue.name.producao-pedido-inclusao}"})
    public void adicionarPedido(String mensagem) {
    	logger.info(String.format("Conteúdo da mensagem: %s", new Object[] {mensagem}));
    	
    	Pedido pedido = mapper.toModel(mensagem);
    	controller.adicionarPedidoAFilaDeProducao(pedido.getPedidoId());
    }
}
