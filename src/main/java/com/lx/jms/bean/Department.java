package com.lx.jms.bean;

import java.io.Serializable;

/**
 * 部门
 */
public class Department implements Serializable {
	
	private static final long serialVersionUID = -104485649797788862L;
	
	private String id;
	private String name;
	private String des;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", des=" + des + "]";
	}
	
}
