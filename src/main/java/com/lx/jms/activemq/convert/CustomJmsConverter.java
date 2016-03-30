package com.lx.jms.activemq.convert;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.lx.jms.bean.Department;
import com.lx.jms.bean.UserInfo;

/**
 * 自定义消息转换器
 * @author lx
 */
public class CustomJmsConverter implements MessageConverter {

	private Logger logger = Logger.getLogger(getClass());
	
	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
	 */
	public Object fromMessage(Message message) throws JMSException, MessageConversionException{
		
		logger.info("============JmsConverter——》fromMessage===============");
		ObjectMessage objMessage = (ObjectMessage) message;
		String dataFlag = objMessage.getStringProperty("dataFlag");
		if("UserInfo".equals(dataFlag)){
			
			UserInfo user = new UserInfo();
			user.setId(objMessage.getStringProperty("id"));
			user.setName(objMessage.getStringProperty("name"));
			return user;
		}else if("Department".equals(dataFlag)){
			
			Department department = new Department();
			department.setId(objMessage.getStringProperty("id"));
			department.setName(objMessage.getStringProperty("name"));
			return department;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
	 */
	public Message toMessage(Object obj, Session session) throws JMSException, MessageConversionException {
		
		logger.info("============JmsConverter——》toMessage===============");
		ObjectMessage objMsg = session.createObjectMessage();    
		if(obj instanceof UserInfo){
			UserInfo user = (UserInfo)obj;
			objMsg.setStringProperty("dataFlag", "UserInfo");
			objMsg.setStringProperty("id", user.getId());
			objMsg.setStringProperty("name", user.getName());
			
		}else if(obj instanceof Department){
			Department dept = (Department) obj;
			objMsg.setStringProperty("dataFlag", "Department");
			objMsg.setStringProperty("id", dept.getId());
			objMsg.setStringProperty("name", dept.getName());
		}
        return objMsg;  
	}

}