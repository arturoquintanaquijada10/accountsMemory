package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ServiceCheckingAccount extends ServiceAccount{
	
	private BigDecimal limit;
	
	

	public ServiceCheckingAccount(@NotNull String account, @NotNull BigDecimal balance, @NotNull String documentNumber,	@NotNull String name, @NotNull String surname, BigDecimal limit) {
		super(account, balance, documentNumber, name, surname);
		this.limit = limit;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	
	
}
