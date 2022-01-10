package com.wba.walgreens.accounts.serviceclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wba.walgreens.accounts.model.Cards;
import com.wba.walgreens.accounts.model.Customers;

@FeignClient("cards")
public interface CardsFeignClient {
    @RequestMapping(method = RequestMethod.POST,value ="cards" ,consumes =  "application/json")
	List<Cards> getAllCards(@RequestHeader("corelation_Id")String corelationId, @RequestBody Customers customers);
}
