package com.sunhill.accountssunhillMemory.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class Balance {
	
	@NotNull
	private String account;	
		
	@NotNull
	private BigDecimal amount;

	public Balance(@NotNull String account, @NotNull BigDecimal amount) {
		super();
		this.account = account;
		this.amount = amount;
	}
	
	public Balance() {}

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
