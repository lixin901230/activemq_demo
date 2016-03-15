package com.lx.jms.activemq.queue;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

import com.lx.jms.utils.SpringContextUtil;

/**
 * 消息消费者（点对点消息模式）—— ActtiveMQ集成Spring
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * @author lx
 */
public class SpringQueueConsumer {
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String xmlPath = "classpath:applicationContext.xml";
		SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
		SpringQueueConsumer consumer = (SpringQueueConsumer) contextUtil.getBean("consumer");
		consumer.receiveMsg();
	}
	
	private JmsTemplate jmsTemplate;
	
	/**
	 * 接收消息
	 */
	public void receiveMsg() {
		try {
			TextMessage message = (TextMessage) jmsTemplate.receive();
			if(message != null) {
				System.out.println("收到消息："+message.getText());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
}
