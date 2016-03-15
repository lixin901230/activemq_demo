package com.lx.jms.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Spring bean工具类2</br>
 * 可以通过该类获取ioc容器中注入的bean对象
 * 
 * 注意：使用该类时必须传入spring bean注入配置文件的路径；比如："classpath:applicationContext.xml"
 * 
 * @author lixin
 */
public class SpringContextUtil {

	private static ApplicationContext ctx;
	
	private SpringContextUtil() {
	}
	
	/**
	 * 实例化对象
	 * @param applicationContextXmlRealPath		spring配置文件路径（applicationContext.xml）
	 * @return
	 */
	public static SpringContextUtil getInstance(String applicationContextXmlRealPath) {
		ctx = getCtx(applicationContextXmlRealPath);
		return new SpringContextUtil();
	}
	
	/**
	 * 初始化spring 应用上下文环境
	 * @param applicationContextXmlRealPath
	 * @return
	 * @throws Exception
	 * @author Administrator
	 */
	public static ApplicationContext getCtx(String applicationContextXmlRealPath) {
		
		try {
			if(StringUtils.isEmpty(applicationContextXmlRealPath)) {
				throw new Exception("spring bean注入配置文件路径为空；applicationContextXmlRealPath="+applicationContextXmlRealPath);
			}
			
			if(!applicationContextXmlRealPath.contains("classpath:")) {	//若为包含该字符串，则说明是绝对路径
				return ctx = new FileSystemXmlApplicationContext(applicationContextXmlRealPath);
			} else {	//相对路径
				return ctx = new ClassPathXmlApplicationContext(applicationContextXmlRealPath);
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取ioc容器中注入的bean
	 * @param beanName
	 * @return
	 * @author Administrator
	 */
	public Object getBean(String beanName) {
		
		try {
			if(ctx != null) {
				return ctx.getBean(beanName);
			} else {
				throw new Exception("获取bean："+beanName+"失败；原因：spring应用上下文环境对象ApplicationContext为null");
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
