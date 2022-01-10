package com.wba.walgreens.gatewayserver.filters;

import java.util.List;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class TracingUtility {
	
	public static final String CORELATION_ID="corelation_Id"; 
	
	public String getCorelationId(HttpHeaders headers) {
		if(headers.get(CORELATION_ID) != null) {
			List<String> requestHeadersList=headers.get(CORELATION_ID);
			//String corelationId=requestHeadersList.stream().findFirst().get();
			
			return requestHeadersList.stream().findFirst().get();
		}
		else {
			return null;
		}
	} 
	
	public ServerWebExchange setRequestHeaders(ServerWebExchange exchange, String name,String value) {
		return exchange.mutate().request(exchange.getRequest().mutate().header(name, value).build()).build();
		
	}
	
	public ServerWebExchange setCorelationId(ServerWebExchange exchange,String value) {
		return this.setRequestHeaders(exchange, CORELATION_ID, value);
	}

}
