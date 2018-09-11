package com.sunhill.accountssunhillMemory.manage;


import java.math.BigDecimal;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.sunhill.accountssunhillMemory.dto.Account;
import com.sunhill.accountssunhillMemory.dto.Balance;
import com.sunhill.accountssunhillMemory.dto.NumberAccount;
import com.sunhill.accountssunhillMemory.dto.Transfers;
import com.sunhill.accountssunhillMemory.exception.GenericException;
import com.sunhill.accountssunhillMemory.util.Constants;


@Component
@Qualifier("manageAccounts")
public class ManageAccounts implements IManageAccounts{
	
	@Autowired
	private Environment env;	
	
	@Autowired
	KeyValueOperations keyValueTemplate;	
	
	@Override
	public  JSONObject createAccount(Account oAccount) {		
		keyValueTemplate.insert(oAccount);	
		return getJsonResponse( env.getProperty("app.message.account.created"));	
	}	
	
	
	@Override
	public  JSONObject getAccountJS(String account) throws GenericException {
		return getJsonResponseObject(getAccount( account));		
	}	
	
	@Override
	public  Account getAccount(String account) throws GenericException {	
		Account oAccount=null;
		try{
			oAccount=keyValueTemplate.findById(account, Account.class).get();
			if(oAccount==null){   throw new GenericException(env.getProperty("app.message.error.notfound"));	}	
		}catch(Exception e){
			throw new GenericException(env.getProperty("app.message.error.notfound"));
		}		
		return oAccount;		
	}	
	
	private boolean allowBalance(Balance oBalance, Account oAccount) throws GenericException{

		if(oBalance.getAmount().signum()==-1){
						
			if(isPositive(oAccount, oBalance , new BigDecimal(0))){
				return true;
			}else{
				if(oAccount.getAccountType().equals(Constants.ACCOUNT_TYPE_CHECKING)){
					if(isPositive(oAccount, oBalance , oAccount.getExtra())){					
						return true;
					}else{
						throw new GenericException(env.getProperty("app.message.operation.notallowed"), HttpStatus.OK);
					}
				}else{
					throw new GenericException(env.getProperty("app.message.operation.notallowed"), HttpStatus.OK);					
				}
			}			
		}else{
			return true;
		}

	}
	
	private boolean isPositive(Account oAccount, Balance oBalance, BigDecimal allowed){
		if(oBalance.getAmount().signum()==-1){
			return ((oAccount.getBalance()).add(oBalance.getAmount()).compareTo(allowed.negate())>0);
		}else{
			return ((oAccount.getBalance()).subtract(oBalance.getAmount()).compareTo(allowed.negate())>0);
		}
	}
	
	@Override
	public  JSONObject manageBalanceAccount(Balance oBalance) throws GenericException {	
		manageBalanceAccount( oBalance, null);
		return getJsonResponse( env.getProperty("app.message.balance.updated"));		
	}

	@Override
	public  boolean manageBalanceAccount(Balance oBalance, Account oAccount) throws GenericException {
		
		if(oAccount==null){
			oAccount=getAccount(oBalance.getAccount());
		}

		allowBalance(oBalance, oAccount);		
		oAccount.setBalance((oAccount.getBalance()).add(oBalance.getAmount()));		
		keyValueTemplate.update(oBalance.getAccount(), oAccount);		
		return true;
	}
	
	
	@Override
	public  JSONObject payInterest(NumberAccount  oNumberAccount ) throws GenericException {
		
		Account oAccount=getAccount(oNumberAccount.getAccount());	
		if(!(Constants.ACCOUNT_TYPE_SAVING.equals(oAccount.getAccountType()))){
			throw new GenericException(env.getProperty("app.message.operation.notallowed"), HttpStatus.OK);
		}
		BigDecimal interest=((oAccount.getBalance()).multiply(oAccount.getExtra())).divide(new BigDecimal(100));		
		manageBalanceAccount(new Balance( oNumberAccount.getAccount(),interest), oAccount);			
		return getJsonResponse( Constants.FIELD_ACCOUNT_INTEREST+env.getProperty("app.message.paid.interest")+interest);				
	}	
	
	@Override
	public  JSONObject calculateInterest(String account) throws GenericException {
		
		Account oAccount=getAccount(account);	
		if(!(Constants.ACCOUNT_TYPE_SAVING.equals(oAccount.getAccountType()))){
			throw new GenericException(env.getProperty("app.message.operation.notallowed"), HttpStatus.OK);
		}			
		return getJsonResponse( Constants.FIELD_ACCOUNT_INTEREST+((oAccount.getBalance()).multiply(oAccount.getExtra())).divide(new BigDecimal(100)));
	}	
	
	@Override
	public  JSONObject doTransfer(Transfers oTransfers) throws Exception {

		try {
			
			Account oAccountFrom=getAccount(oTransfers.getAccountFrom());
			Account oAccountTo=getAccount(oTransfers.getAccountTo());
			
			if(!Constants.ACCOUNT_TYPE_CHECKING.equals(oAccountFrom.getAccountType())   || !Constants.ACCOUNT_TYPE_CHECKING.equals(oAccountTo.getAccountType()) ){
				throw new GenericException(env.getProperty("app.message.operation.notallowed"), HttpStatus.OK);
			}

			if(manageBalanceAccount(new Balance( oTransfers.getAccountFrom(),  (oTransfers.getAmount()).negate()),oAccountFrom)){
				try{
					manageBalanceAccount(new Balance( oTransfers.getAccountTo(),  oTransfers.getAmount()),oAccountTo);			
				} catch (Exception e) {
					manageBalanceAccount(new Balance( oTransfers.getAccountFrom(),  oTransfers.getAmount()),oAccountFrom);
				}
			}
		} catch (Exception e) {
			throw new GenericException(env.getProperty("app.message.error.general"));			
		}

		return getJsonResponse( env.getProperty("app.message.transfer.done"));
	}
	
	private JSONObject getJsonResponse(String sText){
		JSONObject jAccount = new JSONObject();
		jAccount.put(Constants.RESPONSE_RESULT, Constants.RESPONSE_RESULT_OK);		
		jAccount.put(Constants.RESPONSE_MESSAGE, sText);
		return jAccount;
	}
	
	private JSONObject getJsonResponseObject(Object oObject){
		JSONObject jAccount = new JSONObject();
		jAccount.put(Constants.RESPONSE_RESULT, Constants.RESPONSE_RESULT_OK);		
		jAccount.put(Constants.RESPONSE_VALUE, oObject);
		return jAccount;
	}
	
}



