package com.lx.jms.activemq.listener;

import com.lx.jms.bean.Department;
import com.lx.jms.bean.UserInfo;

/**
 * 消费者消息监听器
 * 	监听适配器类{@link org.springframework.jms.listener.adapter.MessageListenerAdapter} 将委派该普通类来作为消息监听器
 * @author lx
 */
public class ConsumerListener {
	
	public void handleMessage(Object baseModel) {
		
		System.out.println(">>>>>>>MessageListenerAdapter————>ConsumerListener>>>>>>");
		String info = "消费者消息监听器接收消息：";
		try {
			if(baseModel instanceof UserInfo) {
				
				UserInfo user = (UserInfo) baseModel;  
			    System.out.println(info+ user +"\n");  
			} else if (baseModel instanceof Department) {
				
				Department dept = (Department) baseModel;  
				System.out.println(info+ dept +"\n");  
			} else {
				System.out.println(info+ baseModel +"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
