package com.wba.walgreens.loans.controller;



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
import com.wba.walgreens.loans.config.LoansServiceConfig;
import com.wba.walgreens.loans.model.Customers;
import com.wba.walgreens.loans.model.Loans;
import com.wba.walgreens.loans.model.LoansProperties;
import com.wba.walgreens.loans.repo.LoansRepository;
@RestController
public class LoansController {
	
	private static final Logger log=LoggerFactory.getLogger(LoansController.class);
	
	@Autowired
	private LoansRepository loansRepository;
	
	@Autowired
	private LoansServiceConfig loansConfig;
	
	
	@PostMapping("/myloans")
	public List<Loans> getLoansDetails(@RequestHeader("corelation_Id")String corelationId, @RequestBody Customers customer){
		System.out.println("calling loans microservices");
		log.info("enter into get loans details method of loans controller ");
		List<Loans> loans=loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		
		if(loans!=null) {
			log.debug("available loans for customer"+customer.getCustomerId(), loans);
			log.info("end of get loans details method of loans controller ");
			return loans;
		}
		else {
			log.error("loans is nor retrieved properly ");
			return null;
		}
	}
	@GetMapping("/loans/properties")
	public String loansConfigReading() throws JsonProcessingException {
		ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		LoansProperties props=new LoansProperties(loansConfig.getMsg(),loansConfig.getBuildVersion(),
				loansConfig.getMailDetails(),loansConfig.getActiveBranches());
		String json=ow.writeValueAsString(props);
		return json;
	}

}
