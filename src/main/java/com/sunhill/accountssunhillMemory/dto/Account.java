package com.sunhill.accountssunhillMemory.dto;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;


public class Account {
	
	@Id
	private String account;
	private BigDecimal balance;		
	private Owner owner;	
	private String accountType;
	private BigDecimal extra;	
	
	
	public Account(String account, BigDecimal balance, Owner owner, String accountType, BigDecimal extra) {		
		this.account = account;
		this.balance = balance;
		this.owner = owner;
		this.accountType = accountType;
		this.extra = extra;
	}	
	
	public Account(){}	
	
	
	@Required
	public String getAccountType() {
		return accountType;
	}

	
	@Required
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
	@Required
	public Owner getOwner() {
		return owner;
	}

	
	@Required
	public void setOwner(Owner owner) {
		this.owner = owner;
	}	
	
	
	
	@Required
	public BigDecimal getBalance() {
		return balance;
	}

	
	@Required
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	
	@Required
	public String getAccount() {
		return account;
	}

	
	@Required
	public void setAccount(String account) {
		this.account = account;
	}

	
	@Required
	public BigDecimal getExtra() {
		return extra;
	}

	
	@Required
	public void setExtra(BigDecimal extra) {
		this.extra = extra;
	}
	
	
}
