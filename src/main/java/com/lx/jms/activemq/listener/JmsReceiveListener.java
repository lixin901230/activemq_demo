package com.lx.jms.activemq.listener;

import com.lx.jms.bean.Department;
import com.lx.jms.bean.UserInfo;

public class JmsReceiveListener {

	public void onMessage(Object baseModel) {
		System.out.println(">>>>>>>JmsReceiveListener>>>>>>");
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
