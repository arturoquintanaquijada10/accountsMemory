package com.sunhill.accountssunhillMemory.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class Transfers {
	
	@NotNull
	private String accountFrom;
	
	@NotNull
	private String accountTo;
	
	@NotNull
	private BigDecimal amount;
	
	

	public Transfers(@NotNull String accountFrom, @NotNull String accountTo, @NotNull BigDecimal amount) {		
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}
	
	public Transfers(){	}

	public String getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}	
	
	

}
