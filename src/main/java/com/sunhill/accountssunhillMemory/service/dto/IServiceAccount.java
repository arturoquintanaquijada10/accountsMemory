package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

public interface IServiceAccount {	
	
	public String getAccount();
	
	public void setAccount(String accountNumber);	
	
	public BigDecimal getBalance();
	
	public void setBalance(BigDecimal balance);	
	
	public String getDocumentNumber();

	public void setDocumentNumber(String documentNumber);

	public String getName();

	public void setName(String name);

	public String getSurname();

	public void setSurname(String surname);
	
}
