package com.sunhill.accountssunhillMemory.manage;

import org.json.simple.JSONObject;

import com.sunhill.accountssunhillMemory.dto.Account;
import com.sunhill.accountssunhillMemory.dto.Balance;
import com.sunhill.accountssunhillMemory.dto.NumberAccount;
import com.sunhill.accountssunhillMemory.dto.Transfers;
import com.sunhill.accountssunhillMemory.exception.GenericException;

public interface IManageAccounts {
	public  JSONObject createAccount(Account oAccount);		
	
	public  JSONObject getAccountJS(String account)throws GenericException;
	
	public  Account getAccount(String account) throws GenericException;
	
	public  JSONObject manageBalanceAccount(Balance oBalance) throws GenericException ;

	public  boolean manageBalanceAccount(Balance oBalance, Account oAccount) throws GenericException ;	
	
	public  JSONObject payInterest(NumberAccount  oNumberAccount ) throws GenericException;
	
	public  JSONObject calculateInterest(String account) throws GenericException ;
	
	public  JSONObject doTransfer(Transfers oTransfers) throws Exception ;
	
}
