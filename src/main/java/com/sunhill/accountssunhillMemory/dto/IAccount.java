package com.sunhill.accountssunhillMemory.dto;

import java.math.BigDecimal;

public interface IAccount {	
	
	public String getAccount();
	
	public void setAccount(String accountNumber);
	
	public Owner getOwner();
	
	public void setOwner(Owner owner);
	
	public BigDecimal getBalance();
	
	public void setBalance(BigDecimal balance);	
	
	public String getAccountType();

	public void setAccountType(String accountType);
	
	public BigDecimal getExtra();

	public void setExtra(BigDecimal extra);
	
}
