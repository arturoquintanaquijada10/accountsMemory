package com.sunhill.accountssunhillMemory.dto;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;


public class Account implements IAccount{
	
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
	
	@Override
	@Required
	public String getAccountType() {
		return accountType;
	}

	@Override
	@Required
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	@Required
	public Owner getOwner() {
		return owner;
	}

	@Override
	@Required
	public void setOwner(Owner owner) {
		this.owner = owner;
	}	
	
	
	@Override
	@Required
	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	@Required
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	@Required
	public String getAccount() {
		return account;
	}

	@Override
	@Required
	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	@Required
	public BigDecimal getExtra() {
		return extra;
	}

	@Override
	@Required
	public void setExtra(BigDecimal extra) {
		this.extra = extra;
	}
	
	
}
