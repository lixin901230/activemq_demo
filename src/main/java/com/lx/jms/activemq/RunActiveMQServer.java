package com.lx.jms.activemq;

import java.io.File;
import java.io.IOException;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.store.kahadb.KahaDBStore;

/**
 * 程序中启动activemq服务
 * @author lx
 */
public class RunActiveMQServer {
	
	public static String jmxDomainName = "jms-broker";
	public static int connectorPort = 2011;
	public static String connectorPath = "/jmxrmi";
	
	public static String queueName = "activemq_queue";
	
	/**
	 * 启动ActiveMQ服务
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			// java代码调用activemq相关的类来构造并启动brokerService
			BrokerService broker = new BrokerService();
			
			// 持久化文件存储位置
			File dataFilterDir = new File("activemq-data/localhost/KahaDB");
			
			KahaDBStore kahaDB = new KahaDBStore();
			kahaDB.setDirectory(dataFilterDir);
			
			// use a bigger journal file
			kahaDB.setJournalMaxFileLength(1024*100);
			
			// small batch means more frequent and smaller writes
			kahaDB.setIndexWriteBatchSize(100);
			
			// do the index write in a separate thread
			kahaDB.setEnableIndexWriteAsync(true);
	        
			broker.setPersistenceAdapter(kahaDB);
			
	        // create a transport connector
	        broker.addConnector("tcp://localhost:61616");
	        broker.setUseJmx(true);
			
			broker.setPersistenceAdapter(kahaDB);
			
			
			ManagementContext context = broker.getManagementContext();
			context.setConnectorPort(connectorPort);
			context.setJmxDomainName(jmxDomainName);
			context.setConnectorPath(connectorPath);
			broker.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
