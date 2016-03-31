package com.lx.jms.activemq.queue;

import javax.jms.JMSException;
import javax.jms.Message;
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
		String xmlPath = "classpath:applicationContext_jms_receiver.xml";
//		xmlPath = "classpath:applicationContext.xml";
		SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
		
		SpringQueueConsumer consumer = (SpringQueueConsumer) contextUtil.getBean("consumer");
		
		//测试阻塞式接收消息
		consumer.receiveMsg();
	}
	
	private JmsTemplate jmsTemplate;
	
	/**
	 * 接收消息
	 */
	public void receiveMsg() {
		try {
			while(true) {
				Message message = jmsTemplate.receive();	//阻塞接收消息（在接收到消息之前，在设置的连接超时时间内，一直处于阻塞状态）
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					if(message != null) {
						System.out.println("收到消息："+textMessage.getText());
					}
				} else {
					throw new Exception("消息类型解析错误");
				}
			}
		} catch (Exception e) {
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
