package com.sunhill.accountssunhillMemory.mapper;

import org.springframework.stereotype.Component;

import com.sunhill.accountssunhillMemory.dto.Account;
import com.sunhill.accountssunhillMemory.dto.Balance;
import com.sunhill.accountssunhillMemory.dto.NumberAccount;
import com.sunhill.accountssunhillMemory.dto.Owner;
import com.sunhill.accountssunhillMemory.dto.Transfers;
import com.sunhill.accountssunhillMemory.service.dto.ServiceBalance;
import com.sunhill.accountssunhillMemory.service.dto.ServiceCheckingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceNumberAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceSavingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceTransfers;
import com.sunhill.accountssunhillMemory.util.Constants;


@Component
public class AccountMapper {

	public Account getSavingAccount(ServiceSavingAccount oServiceSavingAccount){		
		Owner oOwner=new Owner(oServiceSavingAccount.getDocumentNumber(), oServiceSavingAccount.getName() , oServiceSavingAccount.getSurname());		
		return new Account( oServiceSavingAccount.getAccount(),  oServiceSavingAccount.getBalance(),  oOwner, Constants.ACCOUNT_TYPE_SAVING, oServiceSavingAccount.getInterest());
	}
	
	public Account getCheckingAccount(ServiceCheckingAccount oServiceCheckingAccount){		
		Owner oOwner=new Owner(oServiceCheckingAccount.getDocumentNumber(), oServiceCheckingAccount.getName() , oServiceCheckingAccount.getSurname());
		return new Account( oServiceCheckingAccount.getAccount(),  oServiceCheckingAccount.getBalance(),  oOwner, Constants.ACCOUNT_TYPE_CHECKING,  oServiceCheckingAccount.getLimit());		
	}
	
	public Transfers getTransfers(ServiceTransfers oServiceTransfers){		
		 return new Transfers(oServiceTransfers.getAccountFrom(), oServiceTransfers.getAccountTo(), oServiceTransfers.getAmount());			
	}
	
	public Balance getBalance(ServiceBalance oServiceBalance){		
		return new Balance(oServiceBalance.getAccount(),  oServiceBalance.getAmount());
	}
	
	public NumberAccount getNumberAccount(ServiceNumberAccount oServiceNumberAccount){		
		return new NumberAccount(oServiceNumberAccount.getAccount());
	}
	
	
}
