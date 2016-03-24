package com.lx.jms.activemq;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.kahadb.KahaDBStore;

/**
 * 通过程序启动activemq broker服务
 * 	启动ActiveMQ的代理（Broker），有多中方式：
 * 	1、直接运行安装目录的bin\activemq.bat
 * 	2、可以通过在应用程序中以编码的方式启动broker（即本示例中的方法：startBrokerDemo1）
 * 	3、可以通过在应用程序中使用BrokerFactory.createBroker方法来创建broker并启动broker（即本示例中的方法：startBrokerDemo1）
 * @author lx
 */
public class RunBrokerServer {
	
	public static String jmxDomainName = "jms-broker";
	public static int connectorPort = 2011;
	public static String connectorPath = "/jmxrmi";
	
	public static String queueName = "activemq_queue";
	
	/**
	 * 启动ActiveMQ服务
	 * @param args
	 */
	public static void main(String[] args) {
		
//		startBrokerDemo1();
		System.out.println("broker1 启动成功！");
		
		startBrokerDemo2();
		System.out.println("broker2 启动成功！");
	}
	
	/**
	 * 方式1：启动activemq broker服务
	 */
	public static void startBrokerDemo1() {
		try {
			
			//获取初始化消息持久化配置对象
			KahaDBStore kahaDB = initPersistenceConfig();

			// java代码调用activemq相关的类来构造并启动brokerService
			BrokerService broker = new BrokerService();
			broker.setBrokerName("activemq_broker1");
			broker.setPersistenceAdapter(kahaDB);
			
	        // create a transport connector
	        broker.addConnector("tcp://localhost:61616");
	        broker.setUseJmx(true);
			
	        // 以下是ManagementContext的配置，从这个容器中可以取得消息队列中未执行的消息数、消费者数、出队数等等
	        // 设置ManagementContext
			ManagementContext context = broker.getManagementContext();
			context.setConnectorPort(connectorPort);
			context.setJmxDomainName(jmxDomainName);
			context.setConnectorPath(connectorPath);
			
			broker.setManagementContext(context);
			
			// 启动broker服务
			broker.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 方式2：启动activemq broker服务示例2
	 * 	通过BrokerFactory来创建broker服务
	 */
	public static void startBrokerDemo2(){
		try {
			
			//获取初始化消息持久化配置对象
			KahaDBStore kahaDB = initPersistenceConfig();
			
			BrokerService broker = BrokerFactory.createBroker(new URI("broker:tcp://localhost:61616"));
			broker.setBrokerName("activemq_broker2");
			broker.setPersistenceAdapter(kahaDB);
			// 启动broker服务
	        broker.start();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化持久化配置
	 * 初始化配置KahaDB配置信息
	 */
	public static KahaDBStore initPersistenceConfig() {
		// 以下是持久化的配置
		// 持久化文件存储的目录位置（可以是任意目录路径）
		File dataFilterDir = new File("D:/Workspaces/eclipse-jee-luna-SR2/workspace1/activemq_demo/activemq-data/localhost/KahaDB");
		
		KahaDBStore kahaDB = new KahaDBStore();
		kahaDB.setDirectory(dataFilterDir);
		
		// use a bigger journal file
		kahaDB.setJournalMaxFileLength(1024);
		
		// small batch means more frequent and smaller writes
		kahaDB.setIndexWriteBatchSize(100);
		
		// do the index write in a separate thread
		kahaDB.setEnableIndexWriteAsync(true);
		
		return kahaDB;
	}
}
