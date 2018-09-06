package com.sunhill.accountssunhillMemory.service.dto;

import javax.validation.constraints.NotNull;

public class ServiceNumberAccount {
	
	@NotNull
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	

}
