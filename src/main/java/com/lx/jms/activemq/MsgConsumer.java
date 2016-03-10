package com.lx.jms.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息消费者
 * @author lx
 */
public class MsgConsumer {
	
	public static void main(String[] args) {
		
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory = null;
		
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		
		// Session： 一个发送或接收消息的线程
		Session session = null;
		
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination = null;
		
		// 消费者，消息接受者
		MessageConsumer consumer = null;
		
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
			consumer = session.createConsumer(destination);
			
			while (true) {
				
				TextMessage textMessage = (TextMessage) consumer.receive(100000);
				if(textMessage != null) {
					String msg = textMessage.getText();
					System.out.println("收到消息："+msg);
				} else {
					break;
				}
			}
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
}
