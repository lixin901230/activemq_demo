package com.lx.jms.activemq;

import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;

/**
 * 使用 JMX 管理监控 ActiveMQ 的broker
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
	
	public static void main(String[] args) {
		
		try {
			String url = "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi";
			JMXServiceURL serviceURL = new JMXServiceURL(url);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL, null);
			connector.connect();
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			
			System.out.println("Domains:---------------");
			
			String[] domains = connection.getDomains();
			for (int i = 0; i < domains.length; i++) {
				System.out.println("\tDomain["+i+"] = "+domains[i]);				
			}
			
			System.out.println("\nall ObjectName：---------------");
			
			Set<ObjectInstance> set = connection.queryMBeans(null, null);
			for (Iterator<ObjectInstance> iterator = set.iterator(); iterator.hasNext();) {
				ObjectInstance objectInstance = (ObjectInstance) iterator.next();
				System.out.println("\t"+objectInstance.getObjectName());
			}
			
			System.out.println("\n\norg.apache.activemq:brokerName=localhost,type=Broker：---------------");
			
			ObjectName objectName = new ObjectName("org.apache.activemq:brokerName=localhost,type=Broker");
			
			System.out.println("\nBrokerName：---------------");
			BrokerViewMBean mBean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(connection, objectName, BrokerViewMBean.class, true);
			System.out.println("\tBrokerName："+mBean.getBrokerName());
			
			MBeanInfo mBeanInfo = connection.getMBeanInfo(objectName);
			System.out.println("\nClass："+mBeanInfo.getClassName());
			
			MBeanAttributeInfo[] attributes = mBeanInfo.getAttributes();
			if(attributes.length > 0) {
				System.out.println("\nAttribute：---------------");
				for (MBeanAttributeInfo attr : attributes) {
					System.out.println("\t==> Attribute："+ attr.getName());
				}
			}
			MBeanOperationInfo[] operations = mBeanInfo.getOperations();
			if(operations.length > 0) {
				System.out.println("\nOperation：---------------");
				for (MBeanOperationInfo opera : operations) {
					System.out.println("\t==> Operation："+ opera.getName());
				}
			}
			
			connector.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

