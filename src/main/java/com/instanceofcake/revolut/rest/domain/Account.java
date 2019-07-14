package com.instanceofcake.revolut.rest.domain;



public class Account {

	private Integer id;
	private Integer balance;
	private String type;
	
	public Account() {
		super();
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getBalance() {
		return balance;
	}


	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
	
	
	
	
	
}
