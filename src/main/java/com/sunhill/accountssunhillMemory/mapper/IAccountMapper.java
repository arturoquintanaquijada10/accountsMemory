package com.sunhill.accountssunhillMemory.mapper;

import com.sunhill.accountssunhillMemory.dto.Account;
import com.sunhill.accountssunhillMemory.dto.Balance;
import com.sunhill.accountssunhillMemory.dto.NumberAccount;
import com.sunhill.accountssunhillMemory.dto.Transfers;
import com.sunhill.accountssunhillMemory.service.dto.ServiceBalance;
import com.sunhill.accountssunhillMemory.service.dto.ServiceCheckingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceNumberAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceSavingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceTransfers;

public interface IAccountMapper {

	public Account getSavingAccount(ServiceSavingAccount oServiceSavingAccount);
	
	public Account getCheckingAccount(ServiceCheckingAccount oServiceCheckingAccount);
	
	public Transfers getTransfers(ServiceTransfers oServiceTransfers);
	
	public Balance getBalance(ServiceBalance oServiceBalance);
	
	public NumberAccount getNumberAccount(ServiceNumberAccount oServiceNumberAccount);
	
	
}
