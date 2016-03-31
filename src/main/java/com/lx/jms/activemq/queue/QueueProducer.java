package com.lx.jms.activemq.queue;

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
 * 消息生产者（点对点消息模式）
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * @author lx
 */
public class QueueProducer {
	
	private static String brokerURL = "tcp://localhost:61616";
	private static String DESTINATION = "lx.queue";	//消息队列名称DESTINATION在ActveMQ管理界面可以创建或管理，地址：http://localhost:8161/admin/queues.jsp，默认会自动根据传入的参数创建对应名称的队列
	
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
				brokerURL);
			
			// 从连接工厂中构造连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
						
			// 获取操作连接
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			// 得到消息发送的目的地对象消，息队列名称DESTINATION在ActveMQ管理界面可以创建或管理，地址：http://localhost:8161/admin/queues.jsp，默认会自动根据传入的参数创建对应名称的队列
			destination = session.createQueue(DESTINATION);	//createQueue为点对点消息模式，createTopic为发布订阅模式
			
			// 得到消息生产者（发送者）
			producer = session.createProducer(destination);	//如果是要给个目的地发送消息，则创建producer是不指定目的地，在发送的时候在send方法中通过参数指定
			
			// 设置不持久化，仅为了学习，实际项目中根据需求和实际情况而定（不持久化：DeliveryMode.NON_PERSISTENT，持久化：DeliveryMode.PERSISTENT）
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			// 构造消息，此处消息内容写死，实际项目中可通过参数传入消息内容
			send(session, producer);
			
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					session.close();
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
			for (int j = 0; j < 5; j++) {
				String msg = "Queue消息：ActiveMQ 第"+j+"条消息";
				TextMessage message = session.createTextMessage(msg);
				System.out.println("发送消息："+msg);
				
				//发送消息到目的地
				producer.send(message);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
