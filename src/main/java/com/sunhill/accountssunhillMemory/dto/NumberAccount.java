package com.sunhill.accountssunhillMemory.dto;

import javax.validation.constraints.NotNull;

public class NumberAccount {
	
	@NotNull
	private String account;	

	public NumberAccount(@NotNull String account) {
		super();
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	

}
