package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

public class ServiceCheckingAccount extends ServiceAccount{
	
	private BigDecimal limit;

	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	
	
}
