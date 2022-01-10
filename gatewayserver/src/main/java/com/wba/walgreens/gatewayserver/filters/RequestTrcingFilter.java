package com.wba.walgreens.gatewayserver.filters;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
@Order(1)
@Component
public class RequestTrcingFilter implements GlobalFilter {
	
	private  static final Logger logger=LoggerFactory.getLogger(RequestTrcingFilter.class);
	
	@Autowired
	private TracingUtility  utility;
       
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders=exchange.getRequest().getHeaders();
		if(isPresentCorelationId(requestHeaders)) {
		logger.debug("corelation id generated in request tracing filter: {}.", utility.getCorelationId(requestHeaders));	
		}
		else {
			String corelationId=generateCorelationId();	
			exchange=utility.setCorelationId(exchange, corelationId);
			logger.debug("corelation id generated in request tracing filter: {}.", corelationId);
		}
	
		return chain.filter(exchange);
	}

	private String generateCorelationId() {
		
		return UUID.randomUUID().toString();
	}

	private boolean isPresentCorelationId(HttpHeaders requestHeaders) {
		if(utility.getCorelationId(requestHeaders)!=null) {
			return true;
		}
		else {
			return false;			
		}
	}

	
	
	

}
