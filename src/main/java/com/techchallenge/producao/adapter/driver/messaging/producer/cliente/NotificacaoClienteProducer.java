package com.techchallenge.producao.adapter.driver.messaging.producer.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.techchallenge.producao.adapter.driver.messaging.producer.NotificacaoBaseProducer;
import com.techchallenge.producao.adapter.mapper.messaging.MensagemClienteMapper;
import com.techchallenge.producao.core.domain.entities.messaging.Mensagem;

@Component
public class NotificacaoClienteProducer extends NotificacaoBaseProducer<Mensagem> {

	@Value("${cloud.aws.queue.name.notificacao-cliente}")
	private String queueName;
	
	@Autowired
	private MensagemClienteMapper mapper;
	
	@Override
	protected String getQueueName() {
		return queueName;
	}

	@Override
	protected String convertContent(Mensagem value) {
		return mapper.toModel(value);
	}
}
