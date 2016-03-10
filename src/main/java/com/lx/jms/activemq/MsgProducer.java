package com.lx.jms.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息生产者
 * @author lx
 */
public class MsgProducer {
	
	/**
	 * 发送消息测试
	 */
	public static void main(String[] args) {
		
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory = null;
		
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		
		// Session： 一个发送或接收消息的线程
		Session session = null;
		
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination = null;
		
		// MessageProducer：消息发送者
		MessageProducer producer = null;
		
		try {
			//创建一个连接工厂对象
			connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER, 
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, 
				"tcp://localhost:61616");
			
			// 从连接工厂中构造连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
						
			// 获取操作连接
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			// 得到消息发送的目的地对象
			destination = session.createQueue("ActiveMQ");
			
			// 得到消息生产者（发送者）
			producer = session.createProducer(destination);
			
			// 设置不持久化，仅为了学习，实际项目中根据需求和实际情况而定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			// 构造消息，此处消息内容写死，实际项目中可通过参数传入消息内容
			send(session, producer);
			
			session.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * 发送消息
	 * @param session
	 * @param producer
	 */
	public static void send(Session session, MessageProducer producer) {
		
		try {
			//构造消息，此处消息内容写死
			for (int j = 0; j < 1000; j++) {
				TextMessage message = session.createTextMessage("ActiveMQ 发送消息"+j);
				
				System.out.println("发送消息：ActiveMQ 发送消息"+j);
				
				//发送消息到目的地
				producer.send(message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
