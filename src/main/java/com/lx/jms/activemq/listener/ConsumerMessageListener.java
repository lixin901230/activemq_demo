package com.lx.jms.activemq.listener;

import com.lx.jms.bean.Department;
import com.lx.jms.bean.UserInfo;

/**
 * 消费者消息监听器
 * 	监听适配器类{@link org.springframework.jms.listener.adapter.MessageListenerAdapter} 将委派该普通类来作为消息监听器
 * @author lx
 */
public class ConsumerMessageListener {
	
	public void onMessage(Object baseModel) {
		
		System.out.println(">>>>>>>ConsumerMessageListener>>>>>>");
		String info = "消费者消息监听器接受消息：";
		try {
			if(baseModel instanceof UserInfo) {
				
				UserInfo user = (UserInfo) baseModel;  
			    System.out.println(info+ user);  
			} else if (baseModel instanceof Department) {
				
				Department dept = (Department) baseModel;  
				System.out.println(info+ dept);  
			} else {
				System.out.println(info+ baseModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
