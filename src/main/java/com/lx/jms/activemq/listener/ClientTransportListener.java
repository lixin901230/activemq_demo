package com.lx.jms.activemq.listener;

import java.io.IOException;

import org.apache.activemq.transport.TransportListener;

/**
 * activemq服务连接监听器
 * 
 * @author lx
 *
 */
public class ClientTransportListener implements TransportListener {

	/* 
	 * 消息服务器连接发生中断
	 * (non-Javadoc)
	 * @see org.apache.activemq.transport.TransportListener#transportInterupted()
	 */
	@Override
	public void transportInterupted() {
		System.out.println("===>> 断开");
	}
	
	/* 
	 * 恢复与服务器的连接
	 * (non-Javadoc)
	 * @see org.apache.activemq.transport.TransportListener#transportResumed()
	 */
	@Override
	public void transportResumed() {
		System.out.println("===>> 重连");
	}

	/*
	 * 对消息传输命令进行监控
	 *  (non-Javadoc)
	 * @see org.apache.activemq.transport.TransportListener#onCommand(java.lang.Object)
	 */
	@Override
	public void onCommand(Object command) {
		
	}
	
	/* 
	 * 与服务器连接发生错误
	 * (non-Javadoc)
	 * @see org.apache.activemq.transport.TransportListener#onException(java.io.IOException)
	 */
	@Override
	public void onException(IOException error) {
		
	}
}
