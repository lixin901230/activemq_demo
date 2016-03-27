package com.lx.jms.activemq.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.lx.jms.utils.SpringContextUtil;

/**
 * 消息发布者（订阅-发布模式） —— ActtiveMQ集成Spring
 * 
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * @author lx
 */
public class SpringTopicPublisher {
	
	/**
	 * 测试
	 * 	注意：测试时需要先启动 订阅者，再启动 发布者
	 * @param args
	 */
	public static void main(String[] args) {
		String xmlPath = "classpath:applicationContext.xml";
		SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
		SpringTopicPublisher publisher = (SpringTopicPublisher) contextUtil.getBean("publisher");
		publisher.sendMsg();
	}
	
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送消息
	 */
	public void sendMsg() {
		jmsTemplate.send(new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				String msg = "Topic消息：Spring 集成 ActiveMQ 发息";
				TextMessage message = session.createTextMessage(msg);
				System.out.println("发送消息："+msg);
				return message;
			}
		});
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
