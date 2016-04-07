package com.lx.jms.activemq.listener;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

public class ConsumerSessionAwareMessageListener implements SessionAwareMessageListener<TextMessage> {
	
	private Destination destination;
	
	@Override
	public void onMessage(TextMessage message, Session session) throws JMSException {
		System.out.println(">>>>>>>ConsumerSessionAwareMessageListener>>>>>>");
		String info = "消费者消息监听器接收消息：";
		System.out.println(info+ message.getText());
		
		//回复消息
		MessageProducer producer = session.createProducer(destination);
		TextMessage textMessage = session.createTextMessage("消息回复：消息监听器ConsumerSessionAwareMessageListener收到消息");
		producer.send(textMessage);
	}

	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
}
