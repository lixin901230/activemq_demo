package com.lx.jms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring bean获取工具类</br>
 * 可以获取通过配置文件和注解注入的bean
 * 
 * 注意：使用该工具类时，必需先将该工具类注入到ioc中，
 * 		否则容器将无法通过接口ApplicationContextAware的setApplicationContext(ApplicationContext applicationContext)方法给我回传实例化好的上下文环境对象
 * 	
 * @author lx
 *
 */
public class ApplicationContextHelper implements ApplicationContextAware {
	
	private static Logger logger = LoggerFactory.getLogger(ApplicationContextHelper.class);
	private static ApplicationContext context;
	
	/**
	 * 此方法可以把ApplicationContext对象inject到当前类中作为一个静态成员变量。
	 * @throws BeansException 
	 * @param applicationContext ApplicationContext 对象.
	 * @author lixin  
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		logger.info("ApplicationContextHelper.setApplicationContext>>>>"+applicationContext);
		context = applicationContext;
	}

	/**
	 * 获取ApplicationContext 
	 * @return
	 * @author lixin
	 */
	public static ApplicationContext getContext() {
		return context;
	}

	/**
	 * 通过注入到IOC容器中的bean的名称快速获取对应的bean对象
	 * @param beanName	需要获取注入的bean的名称	
	 * @return Object 返回一个Object类型实例，但实际类型为获取到的对应的bean的类型
	 * @author lixin
	 */
	public static Object getBean(String beanName) {
		logger.info("ApplicationContextHelper.getBean()>>>>context="+context);
		return context.getBean(beanName);
	}
}
