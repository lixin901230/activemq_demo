package com.lx.jms.activemq.convert;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
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
		
		logger.info("\n============Into CustomJmsConverter——》fromMessage===============\n");
		
		if(message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			logger.info("\n============Out CustomJmsConverter——》fromMessage===============\n");
			return text;
		} else {
			ObjectMessage objMessage = (ObjectMessage) message;
			String dataFlag = objMessage.getStringProperty("dataFlag");
			if("UserInfo".equalsIgnoreCase(dataFlag)) {
				
				UserInfo user = new UserInfo();
				user.setId(objMessage.getStringProperty("id"));
				user.setName(objMessage.getStringProperty("name"));
				logger.info("\n============Out CustomJmsConverter——》fromMessage===============\n");
				
				return user;
			} else if("Department".equalsIgnoreCase(dataFlag)) {
				
				Department department = new Department();
				department.setId(objMessage.getStringProperty("id"));
				department.setName(objMessage.getStringProperty("name"));
				logger.info("\n============Out CustomJmsConverter——》fromMessage===============\n");
				return department;
			} else if("TextMessage".equalsIgnoreCase(dataFlag)) {

				String string = objMessage.getStringProperty("textMsgName");
				logger.info("\n============Out CustomJmsConverter——》fromMessage===============\n");
				return string;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
	 */
	public Message toMessage(Object obj, Session session) throws JMSException, MessageConversionException {
		
		logger.info("\n============CustomJmsConverter——》toMessage===============\n");
		ObjectMessage objMsg = session.createObjectMessage();    
		if(obj instanceof UserInfo) {
			UserInfo user = (UserInfo)obj;
			objMsg.setStringProperty("dataFlag", "UserInfo");
			objMsg.setStringProperty("id", user.getId());
			objMsg.setStringProperty("name", user.getName());
			
		} else if(obj instanceof Department) {
			Department dept = (Department) obj;
			objMsg.setStringProperty("dataFlag", "Department");
			objMsg.setStringProperty("id", dept.getId());
			objMsg.setStringProperty("name", dept.getName());
		} else if(obj instanceof String) {
			TextMessage textMessage = new ActiveMQTextMessage();
			objMsg.setStringProperty("dataFlag", "TextMessage");
			textMessage.setStringProperty("textMsgName", "消息转换器处理————》这是一个文本消息");
			return textMessage;
		}
        return objMsg;  
	}

}
