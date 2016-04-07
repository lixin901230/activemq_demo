package com.lx.jms.activemq.topic;

import org.junit.Test;

import com.lx.jms.utils.SpringContextUtil;

import junit.framework.TestCase;

/**
 * spring 集成 activemq 订阅-发布模式   消息 的 订阅、发布 及持久化订阅  测试 
 * 
 *  说明：
 * 	本测试测试内容：
 * 		1）、消息生产者发布消息
 * 		2）、消息订阅者同步接收消息（注意：同步模式时，需要先启动订阅者，再启动发布者发布消息）
 * 		3）、消息订阅者异步接收消息
 * 
 * @author lx
 */
public class SpringTopicTest extends TestCase {
	
	/**
	 * 订阅发布模式————发布者发布消息
	 */
	@Test
	public void	testSpringTopicPublisher() {
		
		String xmlPath = "classpath:applicationContext_jms_sender.xml";
		SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
		SpringTopicPublisher publisher = (SpringTopicPublisher) contextUtil.getBean("publisher");
		
		publisher.sendMsg();
		System.exit(-1);	//发完消息结束进程
	}
	
	/**
	 * 测试异步接收消息
	 * 	通过配置文件中的消息接收监听器进行异步接受处理消息，故只需要加载配置即可
	 */
	@Test
	public void	testSpringTopicSubscriberAsyncReceiveMsg() {
		
		try {
			String xmlPath = "classpath:applicationContext_jms_topic_async_receiver.xml";
			SpringContextUtil.getInstance(xmlPath);
			
			Thread.sleep(5 * 60 * 1000);	//便于测试接收消息，此处等待5分钟
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试同步接收消息（注意：订阅-发布模式中  测试同步接收消息时；需要先启动订阅者，再启动发布者发布消息）
	 * 	通过Spring 提供的jms工具类JmsTemplate类提供的接口同步阻塞式接收消息
	 * 
	 */
	@Test
	public void	testSpringTopicSubscriberBlockReceiveMsg() {
		
		try {
			String xmlPath = "classpath:applicationContext_jms_topic_block_receiver.xml";
			SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
			SpringTopicSubscriber subscriber = (SpringTopicSubscriber) contextUtil.getBean("subscriber");
			subscriber.receiveMsg();
			
			Thread.sleep(5 * 60 * 1000);	//便于测试接收消息，此处等待5分钟
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
