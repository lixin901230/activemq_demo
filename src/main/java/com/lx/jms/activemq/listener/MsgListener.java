package com.lx.jms.activemq.listener;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

/**
 * 消息处理监听（消费者通过消息监听器进行异步处理消息）
 * 
 * @author lx
 */
public class MsgListener implements MessageListener {

	public static final int BUFFER_SIZE = 4096;
	
	@Override
	public void onMessage(Message message) {
		
		try {
			if(message instanceof TextMessage) {			//处理文本消息
				handleTextMessage((TextMessage) message);
			
			} else if(message instanceof ObjectMessage) {	//处理对象消息
				handleObjectMessage((ObjectMessage) message);

			} else if(message instanceof MapMessage) {		//处理Map集合消息
				handleMapMessage((MapMessage) message);

			} else if(message instanceof StreamMessage) {	//处理字符流消息
				handleStreamMessage((StreamMessage) message);
				
			} else if (message instanceof BytesMessage) {	//处理二进制消息
				handleBytesMessage((BytesMessage) message);

			} else {
				throw new Exception("消息类型错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 处理文本消息
	 * @throws JMSException 
	 */
	public void handleTextMessage(TextMessage textMessage) throws JMSException {
		try {
			String textMsg = textMessage.getText();
			System.out.println("收到消息："+textMsg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理对象消息（注意：在发送对象消息是，被传递的对象必须序列化）
	 * @throws JMSException 
	 */
	public void handleObjectMessage(ObjectMessage objectMessage) throws JMSException {
		
	}
	
	/**
	 * 处理Map消息
	 * @throws JMSException 
	 */
	public void handleMapMessage(MapMessage mapMessage) throws JMSException {
		String id = mapMessage.getString("id");
		String text = mapMessage.getString("text");
		long times = mapMessage.getLong("times");
		
		System.out.println("接收到消息：id"+id+"----text="+text+"----times="+times);
	}
	
	/**
	 * 处理bytes二进制消息
	 * @throws JMSException 
	 */
	public void handleBytesMessage(BytesMessage bytesMessage) throws JMSException {
		
	}
	
	/**
	 * 处理字符流消息
	 * @throws JMSException 
	 */
	public void handleStreamMessage(StreamMessage streamMessage) throws JMSException {
		
	}

}
