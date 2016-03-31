package com.lx.jms.activemq.queue;

import java.util.Random;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.lx.jms.utils.SpringContextUtil;

/**
 * 消息生产者（点对点消息模式）—— ActtiveMQ集成Spring
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * @author lx
 */
public class SpringQueueProducer {
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String xmlPath = "classpath:applicationContext_jms_sender.xml";
//		xmlPath = "classpath:applicationContext.xml";
		SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
		SpringQueueProducer producer = (SpringQueueProducer) contextUtil.getBean("producer");
		producer.sendMsg();
		System.exit(-1);	//发完消息结束进程
	}
	
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送消息
	 * 	若要测试消息持久化：发送消息后，查看数据库：activemq_msgs，发现发送的消息都持久化到表中了
	 */
	public void sendMsg() {
		
		//循环发送5条消息
		for (int i = 0; i < 5; i++) {
			
			final String count = String.valueOf(i);
			jmsTemplate.send(new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					
					String msg = "Queue消息：Spring 集成 ActiveMQ 消息："+count;
					TextMessage message = session.createTextMessage(msg);
					System.out.println("发送消息："+msg);
					return message;
				}
			});
		}
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
