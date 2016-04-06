package com.lx.jms.activemq.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 消息生产者（点对点消息模式）—— ActtiveMQ集成Spring
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * 
 * 测试说明：
 * 	发送消息测试：{@link SpringQueueTest#testStartSpringQueueProducer()}
 * 
 * @author lx
 */
public class SpringQueueProducer {
	
	private JmsTemplate jmsTemplate;
	private Destination destinationQueue;	//队列名称，给指定队列发送消息时使用
	
	/**
	 * 发送消息
	 * 	若要测试消息持久化：发送消息后，查看数据库：activemq_msgs，发现发送的消息都持久化到表中了
	 * 
	 * 	注意：发送消息 测试详见：{@link SpringQueueTest#testStartSpringQueueProducer()}
	 */
	public void sendMsg() {
		
		//循环发送5条消息
		for (int i = 0; i < 5; i++) {
			
			final String count = String.valueOf(i);
			//给默认队列发送消息
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					
					String msg = "Queue消息：Spring 集成 ActiveMQ 消息："+count;
					TextMessage message = session.createTextMessage(msg);
					System.out.println("发送消息："+msg);
					return message;
				}
			});
			
			//给指定的队列发送消息
			/*jmsTemplate.send(destinationQueue, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					
					String msg = "Queue消息：Spring 集成 ActiveMQ 消息："+count;
					TextMessage message = session.createTextMessage(msg);
					System.out.println("发送消息："+msg);
					return message;
				}
			});*/
		}
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	public Destination getDestinationQueue() {
		return destinationQueue;
	}
	public void setDestinationQueue(Destination destinationQueue) {
		this.destinationQueue = destinationQueue;
	}
}
