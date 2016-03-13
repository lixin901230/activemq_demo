package com.lx.jms.activemq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.lx.jms.activemq.listener.MsgListener;

/**
 * 消息消费者（点对点消息模式）
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * @author lx
 */
public class QueueConsumer {
	
	private static String brokerURL = "tcp://localhost:61616";
	private static String DESTINATION = "lx.queue";	//消息队列名称DESTINATION在ActveMQ管理界面可以创建或管理，地址：http://localhost:8161/admin/queues.jsp，默认会自动根据传入的参数创建对应名称的队列
	
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
				brokerURL);
			
			// 从连接工厂中构造连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			
			// 获取操作连接
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
			// 得到消息发送的目的地对象，消息队列名称DESTINATION在ActveMQ管理界面可以创建或管理，地址：http://localhost:8161/admin/queues.jsp，默认会自动根据传入的参数创建对应名称的队列
			destination = session.createQueue(DESTINATION);	//createQueue为点对点消息模式，createTopic为发布订阅模式
			
			// 得到消息生产者（发送者）
			consumer = session.createConsumer(destination);
			
			//消费消息处理的两种按时：1、阻塞方式receive；2、注册消息处理监听的异步处理方式
			//1、通过阻塞方式接收消息
			/*while (true) {
				//采用receive()阻塞时接受消息，设置接收者接收消息的时间，为了便于测试，这里谁定为100s，此处可以使用消息监听实现异步消息处理
				TextMessage textMessage = (TextMessage) consumer.receive(5000);
				if(textMessage != null) {
					String msg = textMessage.getText();
					System.out.println("收到消息："+msg);
				} else {
					break;
				}
			}*/
			
			//2、通过注册消息处理监听器进行异步消息处理（为了测试方便，此处直接创建匿名对象进行异步消息处理，实际项目中，可自行实现MessageListener接口来进行消息处理）
			consumer.setMessageListener(new MsgListener());
			/*consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					try {
						String textMsg = ((TextMessage) message).getText();
						System.out.println("收到消息："+textMsg);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});*/
			
			Thread.sleep(3000);
			
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
}
