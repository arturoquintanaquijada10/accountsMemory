package com.sunhill.accountssunhillMemory.manage;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;
import org.springframework.data.keyvalue.core.query.KeyValueQuery;

import org.springframework.stereotype.Component;


import com.sunhill.accountssunhillMemory.dto.Account;
import com.sunhill.accountssunhillMemory.dto.Balance;
import com.sunhill.accountssunhillMemory.dto.NumberAccount;
import com.sunhill.accountssunhillMemory.dto.Owner;
import com.sunhill.accountssunhillMemory.dto.Transfers;
import com.sunhill.accountssunhillMemory.exception.GenericException;
import com.sunhill.accountssunhillMemory.util.Constants;





@Component
@Qualifier("manageAccounts")
public class ManageAccounts {
	
	@Autowired
	private Environment env;	
	
	@Autowired
	KeyValueOperations keyValueTemplate;	
	
	public  JSONObject createAccount(Account oAccount) {
		
		keyValueTemplate.insert(oAccount);				
		JSONObject jAccount = new JSONObject();
		jAccount.put(Constants.RESPONSE_RESULT, env.getProperty("app.message.account.created"));
		
		return jAccount;
		
		
	}	
	
	public  Account getAccount(String account) throws GenericException {	
		
		Account oAccount=keyValueTemplate.findById(account, Account.class).get();
		if(oAccount==null){
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
						throw new GenericException(env.getProperty("app.message.operation.notallowed"));
					}
				}else{
					throw new GenericException(env.getProperty("app.message.operation.notallowed"));					
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
	
	
	public  boolean manageBalanceAccount(Balance oBalance) throws GenericException {			
		return manageBalanceAccount( oBalance, null);
	}

	public  boolean manageBalanceAccount(Balance oBalance, Account oAccount) throws GenericException {
		
		if(oAccount==null){
			oAccount=getAccount(oBalance.getAccount());
		}

		allowBalance(oBalance, oAccount);		
		oAccount.setBalance((oAccount.getBalance()).add(oBalance.getAmount()));		
		keyValueTemplate.update(oBalance.getAccount(), oAccount);		
		return true;
	}
	
	
	
	public  JSONObject payInterest(NumberAccount  oNumberAccount ) throws GenericException {
		
		Account oAccount=getAccount(oNumberAccount.getAccount());	
		if(!(Constants.ACCOUNT_TYPE_SAVING.equals(oAccount.getAccountType()))){
			throw new GenericException(env.getProperty("app.message.operation.notallowed"));
		}
		BigDecimal interest=((oAccount.getBalance()).multiply(oAccount.getExtra())).divide(new BigDecimal(100));		
		manageBalanceAccount(new Balance( oNumberAccount.getAccount(),interest), oAccount);		
		JSONObject jAccount = new JSONObject();
		jAccount.put(Constants.FIELD_ACCOUNT_INTEREST, env.getProperty("app.message.paid.interest")+interest);		
		return jAccount;
				
	}	
	
	public  JSONObject calculateInterest(String account) throws GenericException {
		
		Account oAccount=getAccount(account);	
		if(!(Constants.ACCOUNT_TYPE_SAVING.equals(oAccount.getAccountType()))){
			throw new GenericException(env.getProperty("app.message.operation.notallowed"));
		}		
		JSONObject jAccount = new JSONObject();
		jAccount.put(Constants.FIELD_ACCOUNT_INTEREST, ((oAccount.getBalance()).multiply(oAccount.getExtra())).divide(new BigDecimal(100)));
		
		return jAccount;
	}	
	
	public  JSONObject doTransfer(Transfers oTransfers) throws Exception {

		try {
			
			Account oAccountFrom=getAccount(oTransfers.getAccountFrom());
			Account oAccountTo=getAccount(oTransfers.getAccountTo());
			
			if(!Constants.ACCOUNT_TYPE_CHECKING.equals(oAccountFrom.getAccountType())   || !Constants.ACCOUNT_TYPE_CHECKING.equals(oAccountTo.getAccountType()) ){
				throw new GenericException(env.getProperty("app.message.operation.notallowed"));
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

		JSONObject jAccount = new JSONObject();
		jAccount.put(Constants.RESPONSE_RESULT, env.getProperty("app.message.transfer.done"));
		return jAccount;	
	}
	
	
	/*
	@Autowired
	private Environment env;	

	@Autowired
	private MongoClient mongoClient;

	@Autowired
	MongoDbFactory mongoDbFactory;

	@Autowired
	MongoTemplate mongoTemplate;		 

	public  Document doTransfer(Transfers oTransfers) throws Exception {

		ClientSession session = mongoClient.startSession(); 

		try {
			session.startTransaction(TransactionOptions.builder().writeConcern(WriteConcern.MAJORITY).build());

			manageBalanceAccount(new Balance( oTransfers.getAccountFrom(),  (oTransfers.getAmount()).negate()), session);
			manageBalanceAccount(new Balance( oTransfers.getAccountTo(),  oTransfers.getAmount()), session);			

			session.commitTransaction();
			
		} catch (MongoCommandException e) {
			session.abortTransaction();
			throw e;
		} catch (Exception e) {
			session.abortTransaction();
			throw e;		
		} finally {
			session.close();
		}
		return new Document(Constants.RESPONSE_RESULT,env.getProperty("app.message.savingaccount.created"));
	}
	

	public  Document createAccount(Account oAccount) {
		mongoTemplate.insert(oAccount);
		return new Document(Constants.RESPONSE_RESULT,env.getProperty("app.message.account.created"));
		
	}	
	
	public  Document calculateInterest(String account) throws GenericException {
		Query query = new Query();
		query.addCriteria(Criteria.where(Constants.COLLECTION_ID).is(account).and(Constants.FIELD_ACCOUNT_TYPE).is(Constants.ACCOUNT_TYPE_SAVING));		
		Account oAccount=mongoTemplate.findOne(query, Account.class);		
		if(oAccount==null){
			throw new GenericException(env.getProperty("app.message.error.notfound"));
		}		
		return new Document(Constants.FIELD_ACCOUNT_INTEREST,((oAccount.getBalance()).multiply(oAccount.getExtra())).divide(new BigDecimal(100)));
	}	
	
	public  Account getAccount(String account) throws GenericException {
		Query query = new Query();
		query.addCriteria(Criteria.where(Constants.COLLECTION_ID).is(account));		
		Account oAccount=mongoTemplate.findOne(query, Account.class);		
		if(oAccount==null){
			throw new GenericException(env.getProperty("app.message.error.notfound"));
		}		
		return oAccount;
	}	
	
	public  Document payInterest(NumberAccount  oNumberAccount ) throws GenericException {		
		Document interest=calculateInterest( oNumberAccount.getAccount());		
		manageBalanceAccount(new Balance( oNumberAccount.getAccount(),  (BigDecimal)interest.get(Constants.FIELD_ACCOUNT_INTEREST)));
		return new Document(Constants.RESPONSE_RESULT,env.getProperty("app.message.paid.interest")+interest);		
	}	

	public  UpdateResult manageBalanceAccount(Balance oBalance) throws GenericException {
		return manageBalanceAccount( oBalance, null);		
	}

	public  UpdateResult manageBalanceAccount(Balance oBalance, ClientSession session) throws GenericException {

		allowBalance(oBalance);

		MongoCollection<Document> collectionAccounts=mongoTemplate.getCollection(Constants.COLLECTION_ACCOUNT_NAME);
		Bson filterAccount = eq(Constants.COLLECTION_ID, oBalance.getAccount());		
		Bson incrementBalance = inc(Constants.FIELD_ACCOUNT_BALANCE, oBalance.getAmount());				

		if(session == null){
			return collectionAccounts.updateOne( filterAccount, incrementBalance);	
		}else{
			return collectionAccounts.updateOne( session, filterAccount, incrementBalance);	 
		}

	}
	
	private boolean allowBalance(Balance oBalance) throws GenericException{

		if(oBalance.getAmount().signum()==-1){
			Account oAccount =getAccount(oBalance.getAccount());			
			
			if(isPositive(oAccount, oBalance , new BigDecimal(0))){
				return true;
			}else{
				if(oAccount.getAccountType().equals(Constants.ACCOUNT_TYPE_CHECKING)){
					if(isPositive(oAccount, oBalance , oAccount.getExtra())){					
						return true;
					}else{
						throw new GenericException(env.getProperty("app.message.operation.notallowed"));
					}
				}else{
					throw new GenericException(env.getProperty("app.message.operation.notallowed"));					
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
	*/
}



