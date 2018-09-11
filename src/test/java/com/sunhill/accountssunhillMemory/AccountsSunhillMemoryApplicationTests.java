package com.sunhill.accountssunhillMemory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunhill.accountssunhillMemory.service.dto.ServiceBalance;
import com.sunhill.accountssunhillMemory.service.dto.ServiceCheckingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceNumberAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceSavingAccount;
import com.sunhill.accountssunhillMemory.service.dto.ServiceTransfers;
import com.sunhill.accountssunhillMemory.util.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={AccountsSunhillMemoryApplication.class})
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountsSunhillMemoryApplicationTests {
	
	@Autowired
	private Environment env;	

	@Test
	public void contextLoads() {
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountsSunhillMemoryApplicationTests.class);

	private String checkingAccount1="11111";
	private String checkingAccount2="22222";
	private String savingAccount1="33333";

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
				.content(this.getJsonCheckingAccount(checkingAccount1, new BigDecimal(100.50),  Constants.ACCOUNT_TYPE_CHECKING, new BigDecimal(50.50),"025648654R", "John", "Rambo"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful())          
		.andExpect(jsonPath("$.result").value(Constants.RESPONSE_RESULT_OK));
		LOGGER.info("*********************** test01_CreateCheckingAccount1 FIN ********************************");
		
	}	
	
	
	
	@Test
	public void test02_GetAccount1() throws Exception {		

		LOGGER.info("*********************** test02_GetAccount1 INIT ********************************");

		mockMvc.perform(get("/accounts/getAccount?account="+checkingAccount1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())         
				.andExpect(jsonPath("$.result").value(Constants.RESPONSE_RESULT_OK))
				.andExpect(jsonPath("$.value.account").value(checkingAccount1));
		
		LOGGER.info("*********************** test02_GetAccount1 FIN ********************************");		
	}	
	
	@Test
	public void test03_CreateCheckingAccount2() throws Exception {		

		LOGGER.info("*********************** test03_CreateCheckingAccount2 INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/createCheckingAccount")
				.content(this.getJsonCheckingAccount(checkingAccount2, new BigDecimal(200.50),  Constants.ACCOUNT_TYPE_CHECKING, new BigDecimal(10.53),"023542435T", "Donald", "Duck"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test03_CreateCheckingAccount2 FIN ********************************");		
	}	
	
	@Test
	public void test04_GetAccount2() throws Exception {		

		LOGGER.info("*********************** test04_GetAccount2 INIT ********************************");

		mockMvc.perform(get("/accounts/getAccount?account="+checkingAccount2)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())         
				.andExpect(jsonPath("$.result").value(Constants.RESPONSE_RESULT_OK))
				.andExpect(jsonPath("$.value.account").value(checkingAccount2));
		
		LOGGER.info("*********************** test04_GetAccount2 FIN ********************************");		
	}	
	
	@Test
	public void test05_DoTransferCheckingAccount() throws Exception {		

		LOGGER.info("*********************** test05_DoTransferCheckingAccount INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/doTransfer")
				.content(this.getJsonTransfer(checkingAccount1, checkingAccount2, new BigDecimal(35.50)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test05_DoTransferCheckingAccount FIN ********************************");		
	}	
	
	
	
	@Test
	public void test06_ManageBalanceAccountAdd() throws Exception {		

		LOGGER.info("*********************** test06_ManageBalanceAccountAdd INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/manageBalanceAccount")
				.content(this.getJsonBalance(checkingAccount1, new BigDecimal(90.54)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test06_ManageBalanceAccountAdd FIN ********************************");		
	}	
	
	
	@Test
	public void test07_ManageBalanceAccountSubs() throws Exception {		

		LOGGER.info("*********************** test07_ManageBalanceAccountSubs INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/manageBalanceAccount")
				.content(this.getJsonBalance(checkingAccount1, new BigDecimal(90.54).negate()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test07_ManageBalanceAccountSubs FIN ********************************");		
	}	
	
	
	@Test
	public void test08_ManageBalanceAccountSubsAllow() throws Exception {		

		LOGGER.info("*********************** test08_ManageBalanceAccountSubsAllow INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/manageBalanceAccount")
				.content(this.getJsonBalance(checkingAccount1, new BigDecimal(100.54).negate()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test08_ManageBalanceAccountSubsAllow FIN ********************************");		
	}	
	
	@Test
	public void test09_ManageBalanceAccountSubsNotAllow() throws Exception {		

		LOGGER.info("*********************** test09_ManageBalanceAccountSubsNotAllow INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/manageBalanceAccount")
				.content(this.getJsonBalance(checkingAccount1, new BigDecimal(100.54).negate()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.code").value(Constants.RESPONSE_CODE_ERROR))
				.andExpect(jsonPath("$.message").value(env.getProperty("app.message.operation.notallowed")));
		LOGGER.info("*********************** test09_ManageBalanceAccountSubsNotAllow FIN ********************************");		
	}	
	
	
	
	
	@Test
	public void test10_CreateSavingAccount1() throws Exception {		

		LOGGER.info("*********************** test10_CreateSavingAccount1 INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/createSavingAccount")
				.content(this.getJsonSavingAccount(savingAccount1, new BigDecimal(170.55),  Constants.ACCOUNT_TYPE_SAVING, new BigDecimal(1.75),"0336655442S", "Arnold", "Schwarzenegger"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())          
				.andExpect(jsonPath("$.result").value(Constants.RESPONSE_RESULT_OK));
		LOGGER.info("*********************** test10_CreateSavingAccount1 FIN ********************************");
		
	}	
	
	@Test
	public void test11_GetAccount3() throws Exception {		

		LOGGER.info("*********************** test11_GetAccount3 INIT ********************************");

		mockMvc.perform(get("/accounts/getAccount?account="+savingAccount1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())         
				.andExpect(jsonPath("$.result").value(Constants.RESPONSE_RESULT_OK))
				.andExpect(jsonPath("$.value.account").value(savingAccount1));
		
		LOGGER.info("*********************** test11_GetAccount3 FIN ********************************");		
	}	
	
	@Test
	public void test12_CalculateInterest() throws Exception {		

		LOGGER.info("*********************** test12_CalculateInterest INIT ********************************");

		mockMvc.perform(get("/accounts/calculateInterest?account="+savingAccount1)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())         
				.andExpect(jsonPath("$.result").value(Constants.RESPONSE_RESULT_OK));
		
		LOGGER.info("*********************** test12_CalculateInterest FIN ********************************");		
	}	
	
	
	@Test
	public void test13_PayInterest() throws Exception {		

		LOGGER.info("*********************** test13_PayInterest INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/payInterest")
				.content(this.getJsonNumberAccount(savingAccount1))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.result").value(Constants.RESPONSE_RESULT_OK));
		LOGGER.info("*********************** test13_PayInterest FIN ********************************");		
	}	
	
	@Test
	public void test14_ManageBalanceSavingAccountAdd() throws Exception {		

		LOGGER.info("*********************** test14_ManageBalanceSavingAccountAdd INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/manageBalanceAccount")
				.content(this.getJsonBalance(savingAccount1, new BigDecimal(45.74)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test14_ManageBalanceSavingAccountAdd FIN ********************************");		
	}	
	
	
	@Test
	public void test15_ManageBalanceSavingAccountSubs() throws Exception {		

		LOGGER.info("*********************** test15_ManageBalanceSavingAccountSubs INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/manageBalanceAccount")
				.content(this.getJsonBalance(savingAccount1, new BigDecimal(20.84).negate()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful());
		LOGGER.info("*********************** test15_ManageBalanceSavingAccountSubs FIN ********************************");		
	}	
	
	@Test
	public void test16_ManageBalanceSavingAccountSubsNotAllow() throws Exception {		

		LOGGER.info("*********************** test16_ManageBalanceSavingAccountSubsNotAllow INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/manageBalanceAccount")
				.content(this.getJsonBalance(savingAccount1, new BigDecimal(1000.84).negate()))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.code").value(Constants.RESPONSE_CODE_ERROR))
				.andExpect(jsonPath("$.message").value(env.getProperty("app.message.operation.notallowed")));
		LOGGER.info("*********************** test16_ManageBalanceSavingAccountSubsNotAllow FIN ********************************");		
	}	
	
	
	@Test
	public void test17_CreateCheckingAccount1Duplicated() throws Exception {		

		LOGGER.info("*********************** test17_CreateCheckingAccount1Duplicated INIT ********************************");

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/createCheckingAccount")
				.content(this.getJsonCheckingAccount(checkingAccount1, new BigDecimal(100.50),  Constants.ACCOUNT_TYPE_CHECKING, new BigDecimal(50.50),"025648654R", "John", "Rambo"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())          
		.andExpect(jsonPath("$.code").value(Constants.RESPONSE_CODE_ERROR));
		LOGGER.info("*********************** test17_CreateCheckingAccount1Duplicated FIN ********************************");
		
	}	
	
	
	private String getJsonNumberAccount(String account) throws JsonProcessingException {
		ServiceNumberAccount oNumberAccount=new ServiceNumberAccount( account);	
		oNumberAccount.setAccountNumber(account);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(oNumberAccount);
	}
	
	private String getJsonBalance(String account, BigDecimal amount) throws JsonProcessingException {
		ServiceBalance oBalance=new ServiceBalance( account,   amount);	
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(oBalance);
	}

	
	private String getJsonTransfer( String accountFrom, String accountTo, BigDecimal amount) throws JsonProcessingException {
		ServiceTransfers oServiceTransfers=new ServiceTransfers(  accountFrom,  accountTo,   amount);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(oServiceTransfers);
	}


	private String getJsonCheckingAccount(String account, BigDecimal balance, String accountType, BigDecimal extra, String documentNumber, String name, String surname) throws JsonProcessingException {
		ServiceCheckingAccount oServiceCheckingAccount=new ServiceCheckingAccount( account,   balance,   documentNumber,	  name,   surname,  extra);	
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(oServiceCheckingAccount);
	}
	
	private String getJsonSavingAccount(String account, BigDecimal balance, String accountType, BigDecimal extra, String documentNumber, String name, String surname) throws JsonProcessingException {
		ServiceSavingAccount oServiceCheckingAccount=new ServiceSavingAccount( account,   balance,   documentNumber,	  name,   surname,  extra);	
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(oServiceCheckingAccount);
	}

	
}
