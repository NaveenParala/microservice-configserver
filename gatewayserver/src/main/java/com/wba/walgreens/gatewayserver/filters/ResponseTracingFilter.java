package com.wba.walgreens.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


import reactor.core.publisher.Mono;

@Configuration
public class ResponseTracingFilter {

	@Autowired
	private TracingUtility tracingUtility;
	
	/*
	 * @Autowired Tracer tracer;
	 */
	
	private static final Logger logger=LoggerFactory.getLogger(ResponseTracingFilter.class);
	
	@Bean
	public GlobalFilter postGlobalFilter() {
		return (exchange,chain)->{
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				HttpHeaders requestHeaders=exchange.getRequest().getHeaders();
				String corelationId=tracingUtility.getCorelationId(requestHeaders);
				logger.debug("corelation id in response tracing filter: {}.",corelationId);
				
				exchange.getResponse().getHeaders().add(tracingUtility.CORELATION_ID, corelationId);
			}));
		};
	}
}
