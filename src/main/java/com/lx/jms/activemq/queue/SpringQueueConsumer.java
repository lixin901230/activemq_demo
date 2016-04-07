package com.lx.jms.activemq.queue;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;

import com.lx.jms.bean.Department;
import com.lx.jms.bean.UserInfo;

/**
 * 消息消费者（点对点消息模式）—— ActtiveMQ集成Spring
 * 消息队列消息模式：
 * 	1）、发布订阅模式（publish-subscribe）
 * 	2）、点对点模式（point-to-point）
 * 	3）、（非JMS规范标准提供的模式，为使用过程中衍生出的模式）请求-应答模式（request-response）
 * 
 * 测试说明：
 * 	1）、同步阻塞模式接收消息测试：{@link SpringQueueTest#testSpringQueueConsumerBlockReceiveMsg()}
 * 	2）、监听器异步接收消息测试：{@link SpringQueueTest#testSpringQueueConsumerAsyncReceiveMsg()}
 * 
 * @author lx
 */
public class SpringQueueConsumer {
	
	private JmsTemplate jmsTemplate;
	
	/**
	 * 接收消息
	 * 注意：同步阻塞模式接受消息 测试详见：{@link SpringQueueTest#testSpringQueueConsumerBlockReceiveMsg()}
	 */
	public void receiveMsg() {
		try {
			while(true) {
				
				//方式1：接收消息时，未使用转换器对消息进行转换
				/*Message message = jmsTemplate.receive();	//阻塞接收消息（在接收到消息之前，在设置的连接超时时间内，一直处于阻塞状态）
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					if(message != null) {
						System.out.println("收到消息："+textMessage.getText());
					}
				} else {
					throw new Exception("消息类型解析错误");
				}*/
				
				//方式2：接收消息时，使用自定义转换器对象消息进转换
				Object obj = jmsTemplate.receiveAndConvert();
				if(obj instanceof UserInfo) {
					UserInfo user = (UserInfo) obj;
					System.out.println("收到消息："+ user);
				} else if(obj instanceof Department) {
					Department dept = (Department) obj;
					System.out.println("收到消息："+ dept);
				} else {
					throw new Exception("消息类型解析错误；消息类型："+obj.getClass().getName());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
}
