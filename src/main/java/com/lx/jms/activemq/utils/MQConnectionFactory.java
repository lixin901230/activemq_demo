package com.lx.jms.activemq.utils;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * activemq消息队列连接对象
 * @author lx
 */
public class MQConnectionFactory {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 连接mq消息队列服务的 用户名 */
	private String mqUser;
	
	/** 连接mq消息队列服务的 用户密码 */
	private String mqPwd;
	
	/** 连接mq消息队列服务的 url */
	private String serverUrl;
	
	public MQConnectionFactory(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	public MQConnectionFactory(String mqUser, String mqPwd, String serverUrl) {
		this.mqUser = mqUser;
		this.mqPwd = mqPwd;
		this.serverUrl = serverUrl;
	}
	
	/**
	 * 获取消息队列连接
	 * @param serverUrl	消息队列服务连接地址
	 * @return	返回消息队列连接
	 */
	public Connection getMQConnection() {
		Connection connection = null;
		try {
			if(StringUtils.isEmpty(mqUser)) {
				mqUser = ActiveMQConnection.DEFAULT_USER;
			}
			if(StringUtils.isEmpty(mqPwd)) {
				mqPwd = ActiveMQConnection.DEFAULT_PASSWORD;
			}
			
			// 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					mqUser, mqPwd, serverUrl);
			
			// 从连接工厂获取连接对象
			connection = connectionFactory.createConnection();
			
			//启动连接
			//connection.start();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 关闭mq连接
	 * @param Connection
	 */
	public void closeConnection(Connection connection) {
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			logger.error("mq连接资源关闭失败");
			e.printStackTrace();
		}
	}
	
	
	public String getMqUser() {
		return mqUser;
	}
	public void setMqUser(String mqUser) {
		this.mqUser = mqUser;
	}
	public String getMqPwd() {
		return mqPwd;
	}
	public void setMqPwd(String mqPwd) {
		this.mqPwd = mqPwd;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
}
