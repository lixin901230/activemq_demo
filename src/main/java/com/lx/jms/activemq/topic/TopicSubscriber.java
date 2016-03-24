package com.lx.jms.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.lx.jms.activemq.listener.MsgListener;

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
	
	/**
	 * 启动消息订阅者
	 * 	注意：测试时需要先启动 订阅者，再启动 发布者
	 * @param args
	 * @throws JMSException
	 */
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
			connection.start();
			
			//获取操作连接,默认自动向服务器发送接收成功的响应
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			destination = session.createTopic(DESTINATION);
			
			messageConsumer = session.createConsumer(destination);
			
			//方式1、阻塞式接收消息
			/*while(true) {
				
				//阻塞式接收消息：设置接收者接收消息的时间，为了便于测试，这里设定为10s，在这10秒内，能接收到发布者发送的topic消息
				Message message = messageConsumer.receive(10000);
				
				//Map消息
				//MapMessage msgObj = (MapMessage) message;
				//System.out.println("接收到消息：id"+msgObj.getString("id")+"----text="+msgObj.getString("text")+"----times="+msgObj.getLong("times"));
				
				//文本消息
				TextMessage msgObj = (TextMessage) message;
				if(msgObj != null) {
					System.out.println("收到消息："+msgObj.getText());
				} else {
					continue;
				}
				if(message == null) {
					break;
				}
			}*/
			
			// 方式2、注册消息处理器，异步接收处理消息
			messageConsumer.setMessageListener(new MsgListener());
			Thread.sleep(60000);	//休眠10秒等待接收消息再关闭，若不休眠，测试时可以不关闭session和connection进行测试
			
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
