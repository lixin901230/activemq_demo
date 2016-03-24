package com.lx.jms.activemq;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

/**
 * 测试队列queue中各个状态数
 * @author lx
 *
 */
public class StateTest {
	
	/**
	 * 获取状态
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String url= "service:jmx:rmi:///jndi/rmi://localhost:" 
				+ RunBrokerServer.connectorPort 
				+ RunBrokerServer.connectorPath;
			
			JMXServiceURL serviceURL = new JMXServiceURL(url);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
			connector.connect();
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			
			// 需要注意的是，这里的jms-broker必须和上面配置的名称相同
	        ObjectName name = new ObjectName(RunBrokerServer.jmxDomainName+ ":BrokerName=localhost,Type=Broker");
	        BrokerViewMBean mBean = (BrokerViewMBean)MBeanServerInvocationHandler.newProxyInstance(connection,  
	        		name, BrokerViewMBean.class, true);
	        // System.out.println(mBean.getBrokerName());
	        
	        for(ObjectName queueName : mBean.getQueues()) {
	            QueueViewMBean queueMBean = (QueueViewMBean)MBeanServerInvocationHandler
	            			.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
	            System.out.println("\n------------------------------\n");
	            
	            // 消息队列名称
	            System.out.println("States for queue --- "+ queueMBean.getName());
	            
	            // 队列中剩余的消息数
	            System.out.println("Size --- "+ queueMBean.getQueueSize());
	            
	            // 消费者数
	            System.out.println("Number of consumers --- "+ queueMBean.getConsumerCount());
	            
	            // 出队数
	            System.out.println("Number of dequeue ---"+ queueMBean.getDequeueCount() );
	        }
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
	}
}
