package com.lx.jms.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.lx.jms.activemq.listener.ClientTransportListener;


/**
 * 消息发布者（订阅-发布模式）
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * @author lx
 */
public class TopicPublisher {

	private static String brokerURL = "tcp://localhost:61616";
	private static String DESTINATION = "lx.topic";	//消息队列名称DESTINATION在ActveMQ管理界面可以创建或管理，地址：http://localhost:8161/admin/topics.jsp，默认会自动根据传入的参数创建对应名称的队列
	
	
	/**
	 * 启动消息发布者
	 * 	注意：测试时需要先启动 订阅者，再启动 发布者
	 * @param args
	 * @throws JMSException
	 */
	public static void main(String[] args) throws JMSException {
		
		ConnectionFactory connectionFactory = null;
		
		Connection connection = null;
		
		Session session = null;
		
		Destination destination = null;
		
		MessageProducer producer = null;
		
		try {
			connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER, 
					ActiveMQConnection.DEFAULT_PASSWORD, 
					brokerURL);
			
			connection = connectionFactory.createConnection();
			
			//((ActiveMQConnection) connection).addTransportListener(new ClientTransportListener());	//使用TransportListener 监控连接状态 
			
			connection.start();
			
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			destination = session.createTopic(DESTINATION);	//如果是给多个目标发送，则创建多个destination，并在下一步方法createProducer创建MessageProducer时不指定destination，而在循环给每个目标发送消息是通过在send参数中指定destination
			
			producer = session.createProducer(destination);	//如果是给多方发送的话，此处不指定目标，设置为null
			
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);	//不持久化：NON_PERSISTENT；持久化：PERSISTENT
			sendMessage(session, producer);
			
			Thread.sleep(1000);
			
			session.commit();
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
	
	public static void sendMessage(Session session, MessageProducer producer) throws JMSException {
		
		for (int i = 1; i <= 5; i++) {
			
			String message = "Topic消息：第" + i + "条消息";
			
			//Map消息
			/*MapMessage msgObj = session.createMapMessage();
			msgObj.setString("id", "topic_"+i);
			msgObj.setString("text", message);
			msgObj.setLong("times", new Date().getTime());*/
			
			//文本消息
			TextMessage msgObj = session.createTextMessage(message);
			//发送消息
			producer.send(msgObj);
			
			System.out.println("发送消息："+message);
		}
	}
	
}
