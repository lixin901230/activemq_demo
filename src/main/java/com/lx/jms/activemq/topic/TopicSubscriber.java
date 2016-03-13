package com.lx.jms.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


/**
/**
 * 消息订阅者（订阅-发布模式）
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * @author lx
 */
public class TopicSubscriber {
	
	private static String brokerURL = "tcp://localhost:61616";
	private static String DESTINATION = "lx.topic";	//消息队列名称DESTINATION在ActveMQ管理界面可以创建或管理，地址：http://localhost:8161/admin/topics.jsp，默认会自动根据传入的参数创建对应名称的队列
	
	public static void main(String[] args) throws JMSException {
		
		ConnectionFactory connectionFactory = null;
		
		Connection connection = null;
		
		Session session = null;
		
		Destination destination = null;
		
		MessageConsumer messageConsumer = null;
		
		try {
			connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER, 
					ActiveMQConnection.DEFAULT_PASSWORD, 
					brokerURL);
			
			connection = connectionFactory.createConnection();
			
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			destination = session.createTopic(DESTINATION);
			
			messageConsumer = session.createConsumer(destination);
			
			//阻塞式接收消息
			Message message = messageConsumer.receive();
			MapMessage mapMessage = (MapMessage) message;
			String id = mapMessage.getString("id");
			String text = mapMessage.getString("text");
			long times = mapMessage.getLong("times");
			System.out.println("接收到消息：id"+id+"----text="+text+"----times="+times);
			
			//注册消息处理器，异步接收处理消息
			//messageConsumer.setMessageListener(new MsgListener());
			
			Thread.sleep(2000);	//休眠2秒再关闭
			
			//session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}
}
