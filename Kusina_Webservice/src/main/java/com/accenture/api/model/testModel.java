package com.accenture.api.model;

public class testModel {
	
	private String name;
	private String age;
	
	protected testModel() {
		
	}
	
	public testModel(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	
	
	
	

}
