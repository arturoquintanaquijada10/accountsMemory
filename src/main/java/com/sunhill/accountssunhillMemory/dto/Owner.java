package com.sunhill.accountssunhillMemory.dto;

public class Owner {
	
	private String documentNumber;
	private String name;
	private String surname;
	
	public Owner (String documentNumber, String name, String surname){
		
		this.documentNumber=documentNumber;
		this.name=name;
		this.surname=surname;
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
	
	
	
}
