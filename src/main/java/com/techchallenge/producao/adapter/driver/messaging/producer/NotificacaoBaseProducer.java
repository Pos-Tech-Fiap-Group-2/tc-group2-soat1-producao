package com.techchallenge.producao.adapter.driver.messaging.producer;

import org.springframework.beans.factory.annotation.Autowired;

import io.awspring.cloud.sqs.operations.SqsTemplate;

public abstract class NotificacaoBaseProducer<T> {

	@Autowired
	private SqsTemplate sqsTemplate; 
	
	protected abstract String getQueueName();
	
	protected abstract String convertContent(T value);
	
	public void enviar(T value) {
	    try{
	    	System.out.println(getQueueName());
	    	System.out.println(convertContent(value));
	    	sqsTemplate.send(to -> to.queue(getQueueName()).payload(convertContent(value)));
	    }catch (Exception e) {
	        throw e;
	    }
	}
}
