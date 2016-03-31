package com.lx.jms.activemq.listener;

import com.lx.jms.bean.Department;
import com.lx.jms.bean.UserInfo;

/**
 * 处理监听到的消息
 * @author lx
 */
public class JmsBusinessProcess {
	
	public void onMessage(Object baseModel) {
		System.out.println(">>>>>>>JmsBusinessProcess>>>>>>");
		try {
			if(baseModel instanceof UserInfo) {
				
				UserInfo user = (UserInfo) baseModel;  
			    System.out.println(user);  
			} else if (baseModel instanceof Department) {
				
				Department user = (Department) baseModel;  
				System.out.println(user.getId());  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
