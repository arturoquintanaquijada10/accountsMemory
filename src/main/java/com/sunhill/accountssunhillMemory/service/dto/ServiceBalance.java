package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ServiceBalance {
	
	@NotNull
	private String account;	
		
	@NotNull
	private BigDecimal amount;
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}	
	
	

}
