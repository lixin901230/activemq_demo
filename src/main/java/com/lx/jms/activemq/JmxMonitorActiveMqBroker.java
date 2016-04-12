package com.lx.jms.activemq;

/**
 * 使用JMX管理ActiveMQ 的broker
 * 
 * 我们知道ActiveMQ broker的管理接口是通过JMX方式提供的。
 *	一个简单的访问方式就是通过jconsole，输入service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi
 *	需要注意的是：
 *		1、默认JMX功能是没有打开的，需要在activemq.xml的broker配置上添加useJmx="true"
 *		2、需要在managementContext里，修改为createConnector="true"，（同时这里也可以修改jmx的端口和domain）
 * 
 * @author lx
 *
 */
public class JmxMonitorActiveMqBroker {
	
}
