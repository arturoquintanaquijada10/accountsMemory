package com.sunhill.accountssunhillMemory.service.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Required;

public class ServiceAccount {
	
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
		
	public ServiceAccount(){}
	
	
	public ServiceAccount(@NotNull String account, @NotNull BigDecimal balance, @NotNull String documentNumber,	@NotNull String name, @NotNull String surname) {
		this.account = account;
		this.balance = balance;
		this.documentNumber = documentNumber;
		this.name = name;
		this.surname = surname;
	}
	
	
	
	public String getDocumentNumber() {
		return documentNumber;
	}

	
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getSurname() {
		return surname;
	}

	
	public void setSurname(String surname) {
		this.surname = surname;
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
	
	
}
