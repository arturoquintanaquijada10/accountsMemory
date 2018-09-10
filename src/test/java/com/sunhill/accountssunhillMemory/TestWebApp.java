package com.sunhill.accountssunhillMemory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunhill.accountssunhillMemory.controller.AccountsController;
import com.sunhill.accountssunhillMemory.dto.Account;
import com.sunhill.accountssunhillMemory.dto.Owner;
import com.sunhill.accountssunhillMemory.service.dto.ServiceAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceCheckingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceTransfers;
import com.sunhill.accountssunhillMemory.util.Constants;

public class TestWebApp extends AccountsSunhillMemoryApplicationTests{

	private static final Logger LOGGER = LoggerFactory.getLogger(TestWebApp.class);

	private String checkingAccount1="11111";
	private String checkingAccount2="22222";

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}



	@Test
	public void test01_CreateCheckingAccount1() throws Exception {		

		LOGGER.info("*********************** test01_CreateCheckingAccount1 INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/createCheckingAccount")
				.content(this.getJsonAccount(checkingAccount1, new BigDecimal(100.50),  Constants.ACCOUNT_TYPE_CHECKING, new BigDecimal(50.50),"025648654R", "John", "Rambo"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test01_CreateCheckingAccount1 FIN ********************************");

		
		LOGGER.info("*********************** test02_GetAccount1 INIT ********************************");

		mockMvc.perform(get("/accounts/getAccount?account="+checkingAccount1)).andExpect(status().is2xxSuccessful());		
		
		LOGGER.info("*********************** test02_GetAccount1 FIN ********************************");		
		
		
		LOGGER.info("*********************** test03_CreateCheckingAccount2 INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/createCheckingAccount")
				.content(this.getJsonAccount(checkingAccount2, new BigDecimal(200.50),  Constants.ACCOUNT_TYPE_CHECKING, new BigDecimal(10.53),"023542435R", "Donald", "Duck"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test03_CreateCheckingAccount2 FIN ********************************");		
		
		LOGGER.info("*********************** test04_DoTransferCheckingAccount INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/doTransfer")
				.content(this.getJsonTransfer(checkingAccount1, checkingAccount2, new BigDecimal(35.50)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test04_DoTransferCheckingAccount FIN ********************************");		
		/*
		mockMvc.perform(get("/accounts/getAccount")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.name").value("emp1")).andExpect(jsonPath("$.designation").value("manager"))
				.andExpect(jsonPath("$.empId").value("1")).andExpect(jsonPath("$.salary").value(3000));
		 */
	}	
	
	/*
	
	@Test
	public void test02_GetAccount1() throws Exception {		

		LOGGER.info("*********************** test02_GetAccount1 INIT ********************************");

		mockMvc.perform(get("/accounts/getAccount?account="+checkingAccount1)).andExpect(status().is2xxSuccessful());		
		
		LOGGER.info("*********************** test02_GetAccount1 FIN ********************************");		
	}	
	
	@Test
	public void test03_CreateCheckingAccount2() throws Exception {		

		LOGGER.info("*********************** test03_CreateCheckingAccount2 INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/createCheckingAccount")
				.content(this.getJsonAccount(checkingAccount2, new BigDecimal(200.50),  Constants.ACCOUNT_TYPE_CHECKING, new BigDecimal(10.53),"023542435R", "Donald", "Duck"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test03_CreateCheckingAccount2 FIN ********************************");		
	}	
	
	@Test
	public void test04_DoTransferCheckingAccount() throws Exception {		

		LOGGER.info("*********************** test04_DoTransferCheckingAccount INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/doTransfer")
				.content(this.getJsonTransfer(checkingAccount1, checkingAccount2, new BigDecimal(35.50)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test04_DoTransferCheckingAccount FIN ********************************");		
	}	

	*/
	private String getJsonTransfer( String accountFrom, String accountTo, BigDecimal amount) throws JsonProcessingException {

		ServiceTransfers oServiceTransfers=new ServiceTransfers(  accountFrom,  accountTo,   amount);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(oServiceTransfers);
	}


	private String getJsonAccount(String account, BigDecimal balance, String accountType, BigDecimal extra, String documentNumber, String name, String surname) throws JsonProcessingException {


		ServiceCheckingAccount oServiceCheckingAccount=new ServiceCheckingAccount( account,   balance,   documentNumber,	  name,   surname,  extra);	

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(oServiceCheckingAccount);
	}

	

}




