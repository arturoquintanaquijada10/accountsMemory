package com.sunhill.accountssunhillMemory.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sunhill.accountssunhillMemory.exception.GenericException;
import com.sunhill.accountssunhillMemory.manage.IManageAccounts;
import com.sunhill.accountssunhillMemory.mapper.AccountMapper;
import com.sunhill.accountssunhillMemory.mapper.IAccountMapper;
import com.sunhill.accountssunhillMemory.service.dto.ServiceBalance;
import com.sunhill.accountssunhillMemory.service.dto.ServiceCheckingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceNumberAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceSavingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceTransfers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping( value = "/" )
@Validated
public class AccountsController {

	@Autowired
	IAccountMapper accountMapper;

	@Autowired
	@Qualifier("manageAccounts")
	IManageAccounts oManageAccounts;

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);

	@RequestMapping( value = "/accounts/createSavingAccount", method = RequestMethod.POST )
	@ApiOperation("Create a Saving account")
	public ResponseEntity<Object> createSavingAccount( @Valid @RequestBody ServiceSavingAccount oServiceSavingAccount){
		return  new ResponseEntity<>(oManageAccounts.createAccount(accountMapper.getSavingAccount(oServiceSavingAccount)), HttpStatus.OK);		
	}


	@RequestMapping( value = "/accounts/createCheckingAccount", method = RequestMethod.POST )
	@ApiOperation("Create a Checking account")
	public ResponseEntity<Object> createCheckingAccount( @Valid @RequestBody ServiceCheckingAccount oServiceCheckingAccount){
		return  new ResponseEntity<>(oManageAccounts.createAccount(accountMapper.getCheckingAccount(oServiceCheckingAccount)), HttpStatus.OK);		
	}

	@RequestMapping( value = "/accounts/getAccount", method = RequestMethod.GET )
	@ApiOperation("Get account details")
	public ResponseEntity<Object> getAccount( @ApiParam(value = "account", required = true) @RequestParam(value="account")  String account) throws GenericException{
		return  new ResponseEntity<>(oManageAccounts.getAccountJS( account), HttpStatus.OK);	
	}

	@RequestMapping( value = "/accounts/doTransfer", method = RequestMethod.POST )
	@ApiOperation("Do transfer between Checking accounts")
	public ResponseEntity<Object> doTransfer( @Valid @RequestBody ServiceTransfers oServiceTransfers) throws Exception{					
		return  new ResponseEntity<>(oManageAccounts.doTransfer(accountMapper.getTransfers(oServiceTransfers)), HttpStatus.OK);
	}

	@RequestMapping( value = "/accounts/manageBalanceAccount", method = RequestMethod.PATCH )
	@ApiOperation("Add or subtract balance to a account")
	public ResponseEntity<Object> manageBalanceAccount( @Valid @RequestBody ServiceBalance oServiceBalance) throws GenericException{
		return  new ResponseEntity<>(oManageAccounts.manageBalanceAccount(accountMapper.getBalance(oServiceBalance)), HttpStatus.OK);	
	}

	@RequestMapping( value = "/accounts/calculateInterest", method = RequestMethod.GET )
	@ApiOperation("Calculate interest for savings accounts")
	public ResponseEntity<Object> calculateInterest( @ApiParam(value = "Saving account", required = true) @RequestParam(value="account")  String account) throws GenericException{
		return  new ResponseEntity<>(oManageAccounts.calculateInterest( account), HttpStatus.OK);	
	}

	@RequestMapping( value = "/accounts/payInterest", method = RequestMethod.PATCH )
	@ApiOperation("Pay interest to a saving account")
	public ResponseEntity<Object> payInterest( @Valid @RequestBody ServiceNumberAccount oServiceNumberAccount) throws GenericException{
		return  new ResponseEntity<>(oManageAccounts.payInterest(accountMapper.getNumberAccount(oServiceNumberAccount)), HttpStatus.OK);	
	}


}
