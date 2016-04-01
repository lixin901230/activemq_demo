package com.lx.jms.activemq.topic;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.lx.jms.activemq.queue.SpringQueueTest;
import com.lx.jms.bean.UserInfo;
import com.lx.jms.utils.SpringContextUtil;

/**
 * 消息发布者（订阅-发布模式） —— ActtiveMQ集成Spring
 * 
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * 
 * 测试说明：
 * 	发送消息测试：{@link SpringTopicTest#testSpringTopicPublisher()}
 * 
 * @author lx
 */
public class SpringTopicPublisher {
	
	private JmsTemplate jmsTemplate;
	
	/**
	 * 发送消息
	 * 	注意：发送消息 测试详见：{@link SpringTopicTest#testSpringTopicPublisher()}
	 */
	public void sendMsg() {
		//方式1：循环发送5条消息
		for (int i = 0; i < 5; i++) {
			final String count = String.valueOf(i);
			jmsTemplate.send(new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					String msg = "Topic消息：Spring 集成 ActiveMQ 发息："+count;
					TextMessage message = session.createTextMessage(msg);
					System.out.println("发送消息："+msg);
					return message;
				}
			});
		}
		
		//方式2：对象转换器转换处理后发送
		/*UserInfo userInfo = new UserInfo("11", 21, "lx", 10.0, 70.0f);
		jmsTemplate.convertAndSend(userInfo);*/
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
