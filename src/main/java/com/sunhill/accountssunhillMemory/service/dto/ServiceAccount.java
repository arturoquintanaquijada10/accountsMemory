package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Required;

public class ServiceAccount implements IServiceAccount{
	
	@NotNull
	private String account;
	
	@NotNull
	private BigDecimal balance;	
	
	@NotNull
	private String documentNumber;
	
	@NotNull
	private String name;
	
	@NotNull
	private String surname;
		
	
	
	@Override
	public String getDocumentNumber() {
		return documentNumber;
	}

	@Override
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSurname() {
		return surname;
	}

	@Override
	public void setSurname(String surname) {
		this.surname = surname;
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
	
	
}
