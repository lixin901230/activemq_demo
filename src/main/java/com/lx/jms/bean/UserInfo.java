package com.lx.jms.bean;

import java.io.Serializable;

/**
 * 用于测试ActiveMQ对象消息传递
 * 需要消息传递的对象必须实现序列化接口Serializable
 */
public class UserInfo implements Serializable {
	
	private static final long serialVersionUID = 1523010881760470841L;
	
	private String id;
	private Integer age;
	private String name;
	private Double money;
	private Float bodyWeight;
	
	public UserInfo() {
	}
	
	public UserInfo(String id, Integer age, String name, Double money,
			Float bodyWeight) {
		this.id = id;
		this.age = age;
		this.name = name;
		this.money = money;
		this.bodyWeight = bodyWeight;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Float getBodyWeight() {
		return bodyWeight;
	}
	public void setBodyWeight(Float bodyWeight) {
		this.bodyWeight = bodyWeight;
	}
	
}
