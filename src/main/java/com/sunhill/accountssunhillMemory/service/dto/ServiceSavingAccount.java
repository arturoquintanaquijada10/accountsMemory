package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ServiceSavingAccount extends ServiceAccount{
	
	@NotNull
	private BigDecimal interest;

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	

}
