package com.wba.walgreens.accounts.serviceclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wba.walgreens.accounts.model.Customers;
import com.wba.walgreens.accounts.model.Loans;

@FeignClient("loans")
public interface LoansFeignClient {
    @RequestMapping(method = RequestMethod.POST,value = "myloans", consumes = "application/json")
	List<Loans> getAllLoanDetals(@RequestHeader("corelation_Id") String corelationId, @RequestBody Customers customers);
	
}
