package com.lx.jms.activemq.queue;

import org.junit.Test;

import com.lx.jms.utils.SpringContextUtil;

import junit.framework.TestCase;

/**
 * spring 集成 activemq 点对点模式   消息 的 发送、接受  及 持久化  测试 
 * 
 * 说明：
 * 	本测试测试内容：
 * 		1）、消息生产者发送消息
 * 		2）、消息消费者同步接收消息
 * 		3）、消息消费者异步接收消息
 * 
 * @author lx
 */
public class SpringQueueTest extends TestCase {

	/**
	 * 点对点模式————消息生产者发送消息
	 */
	@Test
	public void testStartSpringQueueProducer() {
		String xmlPath = "classpath:applicationContext_jms_sender.xml";
		SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
		SpringQueueProducer producer = (SpringQueueProducer) contextUtil.getBean("producer");
		producer.sendMsg();
		System.exit(-1);	//发完消息结束进程
	}
	
	/**
	 * 点对点模式————测试异步接收消息（推荐异步模式）
	 * 	通过配置文件中的消息接收监听器进行异步接受处理消息，故只需要加载配置即可
	 */
	@Test
	public void testSpringQueueConsumerAsyncReceiveMsg() {
		try {
			String xmlPath = "classpath:applicationContext_jms_queue_async_receiver.xml";
			SpringContextUtil.getInstance(xmlPath);
			
			Thread.sleep(5 * 60 * 1000);	//便于测试接收消息，此处等待5分钟
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试同步接收消息
	 * 	通过Spring 提供的jms工具类JmsTemplate类提供的接口同步阻塞式接收消息
	 */
	@Test
	public void	testSpringQueueConsumerBlockReceiveMsg() {
		try {
			String xmlPath = "classpath:applicationContext_jms_queue_block_receiver.xml";
			SpringContextUtil contextUtil = SpringContextUtil.getInstance(xmlPath);
			SpringQueueConsumer consumer = (SpringQueueConsumer) contextUtil.getBean("consumer");
			consumer.receiveMsg();	//同步阻塞式接收消息
			
			Thread.sleep(5 * 60 * 1000);	//便于测试接收消息，此处等待5分钟
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
