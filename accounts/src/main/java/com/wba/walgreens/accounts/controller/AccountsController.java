package com.wba.walgreens.accounts.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wba.walgreens.accounts.config.AccountsServiceConfig;
import com.wba.walgreens.accounts.model.Accounts;
import com.wba.walgreens.accounts.model.AccountsProperties;
import com.wba.walgreens.accounts.model.Cards;
import com.wba.walgreens.accounts.model.CustomerDetails;
import com.wba.walgreens.accounts.model.Customers;
import com.wba.walgreens.accounts.model.Loans;
import com.wba.walgreens.accounts.repo.AccountsRepo;
import com.wba.walgreens.accounts.serviceclient.CardsFeignClient;
import com.wba.walgreens.accounts.serviceclient.LoansFeignClient;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;






@RestController
public class AccountsController {
	
	private static final Logger log=LoggerFactory.getLogger(AccountsController.class);
	
	@Autowired
	private AccountsRepo accountsRepo;
	
	@Autowired
	private AccountsServiceConfig accountsConfig;
	
	 @Autowired
	private CardsFeignClient cardsFeignClient;
	
	 @Autowired
	 private LoansFeignClient loansFeignClient;
	 
	 
	
	@PostMapping("/accounts")
	@Timed(value = "getAccountDetails.time",description = "time taken to return accounts details")
	public Accounts getAccountDetails(@RequestBody Customers  customers ) {
		log.info("enter into account controller get accountdetails methods. ");		
		Accounts accounts=accountsRepo.findByCustomerId(customers.getCustomerId());
		
		if(accounts!=null) {
			log.info("end of the account controller get accountdetails methods. ");
			return accounts;
			
		}else {
			return null;
		}
	}

	@GetMapping("/accounts/properties")
	public String propertiesReading() throws JsonProcessingException {
		log.info("enter into account controller's properties reading  methods. ");
		ObjectWriter mapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
		AccountsProperties props=new AccountsProperties(accountsConfig.getMsg(),accountsConfig.getBuildVersion(),
				accountsConfig.getMailDetails(),accountsConfig.getActiveBranches());
		
		String json=mapper.writeValueAsString(props);
		log.info("end of account controller's propertiesreading  methods. ");
		return json;
		
	}
    @PostMapping("/customerDetails")
	/*
	 * @CircuitBreaker(name="detailsForCustomerSupportApp",fallbackMethod =
	 * "getCustomerBankDetailsFallback")
	 */
    @Retry(name = "retryForCustomerSupportApp",fallbackMethod = "getCustomerBankDetailsFallback")
	public CustomerDetails getCustomerBankDetails(@RequestHeader("corelation_Id") String corelationId, @RequestBody Customers customers) {
    	log.info("enter into account controller's get getCustomerBankDetails methods. ");
    	Accounts accountsDetails=accountsRepo.findByCustomerId(customers.getCustomerId());
    	List<Loans> loansList=loansFeignClient.getAllLoanDetals(corelationId,customers);
    	List<Cards> cardsList=cardsFeignClient.getAllCards(corelationId,customers);
    	
    	CustomerDetails customerDetails=new CustomerDetails();
    	customerDetails.setAccounts(accountsDetails);
    	customerDetails.setLoans(loansList);
    	customerDetails.setCards(cardsList);
    	log.info("end of the  account controller's get getCustomerBankDetails methods. ");
    	return customerDetails;
	}
    private CustomerDetails getCustomerBankDetailsFallback(String corelationId,Customers customers,Throwable throwable ) {
    	
    	log.info("enter into  account controller's get getCustomerBankDetails fallback methods. ");
    	
    	Accounts accountsDetails=accountsRepo.findByCustomerId(customers.getCustomerId());
    	List<Loans> loansList=loansFeignClient.getAllLoanDetals(corelationId,customers);
    	//List<Cards> cardsList=cardsFeignClient.getAllCards(customers);
    	
    	CustomerDetails customerDetails=new CustomerDetails();
    	customerDetails.setAccounts(accountsDetails);
    	customerDetails.setLoans(loansList);
    	//customerDetails.setCards(cardsList);
    	log.info("end of the  account controller's get getCustomerBankDetails fallback methods. ");
    	return customerDetails;
    }
	@GetMapping("/sayHello")
	@RateLimiter(name = "helloworld",fallbackMethod = "sayHelloFallback") 
    public String sayHello() {
    	return "hello welcome to rate limiter example";
    }
	
	public String sayHelloFallback(Throwable t) {
		return "hi welcome to rate limiter example from fallback";
	}
	
}
