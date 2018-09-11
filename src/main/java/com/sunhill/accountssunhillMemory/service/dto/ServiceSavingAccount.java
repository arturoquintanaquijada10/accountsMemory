package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ServiceSavingAccount extends ServiceAccount{	

	public ServiceSavingAccount(@NotNull String account, @NotNull BigDecimal balance, @NotNull String documentNumber,
			@NotNull String name, @NotNull String surname, @NotNull BigDecimal interest) {
		super(account, balance, documentNumber, name, surname);
		this.interest = interest;
	}	
		
	public ServiceSavingAccount(){}

	@NotNull
	private BigDecimal interest;

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	

}
