package com.lx.jms.activemq.convert;

import java.text.DateFormat;
import java.text.ParseException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class JmsConverter implements MessageConverter {

	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException{
		System.out.println("fromMessage");
		ObjectMessage objMessage = (ObjectMessage)message;
		String dataFlag = objMessage.getStringProperty("dataFlag");
		/*if("FaUser".equals(dataFlag)){
			JmsFaUser user = new JmsFaUser();
			user.setId(objMessage.getLongProperty("userId"));
			user.setUserName(objMessage.getStringProperty("userName"));
			user.setOfficeEmail(objMessage.getStringProperty("officeEmail"));
			user.setSelfEmail(objMessage.getStringProperty("selfEmail"));
			user.setOfficeTel(objMessage.getStringProperty("officeTel"));
			user.setMobileTel(objMessage.getStringProperty("mobileTel"));
			String lastDate = objMessage.getStringProperty("lastUpdatedDate");
			try {
				if(lastDate != null){
					user.setLastUpdatedDate(DateFormat.getDateTimeInstance().parse(lastDate));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return user;
		}else if("Organize".equals(dataFlag)){
			Long orgId = objMessage.getLongProperty("orgId");
			String orgName = objMessage.getStringProperty("orgName");
			JmsOrganize organize = new JmsOrganize(orgId, orgName);
			String lastDate = objMessage.getStringProperty("lastUpdatedDate");
			try {
				organize.setLastUpdatedDate(DateFormat.getDateTimeInstance().parse(lastDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return organize;
		}*/
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
	 * @date 2012-10-22
	 * @user 
	 */
	public Message toMessage(Object obj, Session session) throws JMSException,
			MessageConversionException {
		ObjectMessage objMsg=session.createObjectMessage();    
		/*if(obj instanceof JmsFaUser){
			JmsFaUser user = (JmsFaUser)obj;
			Logger.getLogger().info("The user message's userId is " + user.getId());
			objMsg.setStringProperty("dataFlag", "FaUser");
			objMsg.setStringProperty("userName", user.getUserName());
			objMsg.setLongProperty("userId", user.getId());
			objMsg.setStringProperty("officeEmail", user.getOfficeEmail());
			objMsg.setStringProperty("selfEmail", user.getSelfEmail());
			if(user.getOfficeTel() != null){
				objMsg.setStringProperty("officeTel", user.getOfficeTel());
			}else{
				objMsg.setLongProperty("officeTel", new Long(0));
			}
			if(user.getMobileTel() != null){
				objMsg.setStringProperty("mobileTel", user.getMobileTel());
			}else{
				objMsg.setLongProperty("mobileTel", new Long(0));
			}
			if(user.getLastUpdatedDate() != null){
				objMsg.setStringProperty("lastUpdatedDate", DateFormat.getDateTimeInstance().format(user.getLastUpdatedDate()));
			}
			
		}else if(obj instanceof JmsOrganize){
			JmsOrganize org = (JmsOrganize)obj;
			Logger.getLogger().info("The org message's userId is " + org.getId());
			objMsg.setStringProperty("dataFlag", "Organize");
			objMsg.setLongProperty("orgId", org.getId());
			objMsg.setStringProperty("orgName", org.getOrgName());
			if(org.getLastUpdatedDate() != null){
				objMsg.setStringProperty("lastUpdatedDate", DateFormat.getDateTimeInstance().format(org.getLastUpdatedDate()));
			}
		}*/
        return objMsg;  
	}

}