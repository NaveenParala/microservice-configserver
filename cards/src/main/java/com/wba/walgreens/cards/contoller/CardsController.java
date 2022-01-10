package com.wba.walgreens.cards.contoller;

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
import com.wba.walgreens.cards.config.CardsServiceConfig;
import com.wba.walgreens.cards.model.Cards;
import com.wba.walgreens.cards.model.CardsProperties;
import com.wba.walgreens.cards.model.Customers;
import com.wba.walgreens.cards.repo.CardsRepository;

@RestController
public class CardsController {
	
	private static final Logger log=LoggerFactory.getLogger(CardsController.class);
  
  @Autowired
  private CardsRepository  cardsRepository;
  
  @Autowired
  private CardsServiceConfig cardsConfig;
  
  @PostMapping("/cards")
  public List<Cards> getCardsDetails(@RequestHeader("corelation_Id")String corelationId,@RequestBody Customers customers){
	  log.info("end of the  account controller's get getCustomerBankDetails methods. ");
	  List<Cards> cards=cardsRepository.findByCustomerId(customers.getCustomerId());
	  if(cards!=null) {
		  log.info("end of the  cards controller's get getcardsDetails methods. ");
		 return  cards;
	  }else {
		  return null;
	  }
	  
  }
  
  @GetMapping("/cards/properties")
	public String propertiesReading() throws JsonProcessingException {
	  log.info("enter into cards controller's get propertiesReading methods. ");
		ObjectWriter mapper=new ObjectMapper().writer().withDefaultPrettyPrinter();
		CardsProperties props=new  CardsProperties(cardsConfig.getMsg(),cardsConfig.getBuildVersion(),
				cardsConfig.getMailDetails(),cardsConfig.getActiveBranches());
		
		String json=mapper.writeValueAsString(props);
		log.info("end of the cards controller's get propertiesReading methods. ");
		return json;
		
	}
	
	
}
