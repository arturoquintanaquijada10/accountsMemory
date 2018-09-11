package com.sunhill.accountssunhillMemory.service.dto;

public class ServiceNumberAccount {
	
	private String accountNumber;
		
	public ServiceNumberAccount(String accountNumber) {		
		this.accountNumber = accountNumber;
	}
	
	public ServiceNumberAccount() {	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	

}
