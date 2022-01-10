package com.wba.walgreens.gatewayserver;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator myrouting(RouteLocatorBuilder routingBuilder) {
		return routingBuilder.routes()
				.route(p->p
				         .path("/walgreens/accounts/**")
				         .filters(f->f.rewritePath("/walgreens/accounts/(?<segment>.*)", "/${segment}")
						 .addResponseHeader("Response-Time", new Date().toString()))
				         .uri("lb://ACCOUNTS"))
				.route(p->p
						.path("/walgreens/loans/**")
						.filters(f->f.rewritePath("/walgreens/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("Response-Time", new Date().toString()))
						.uri("lb://LOANS"))
				/*
				 * .route(p->p .path("/walgreens/loans/**")
				 * .filters(f->f.rewritePath("/walgreens/loans/(?<segment>.*)","/${segment})")
				 * .addResponseHeader("ResponseHeader-Timestamp", new Date().toString()))
				 * .uri("lb://LOANS") )
				 */
				.route(p->p
						.path("/walgreens/cards/**")
						.filters(f->f.rewritePath("/walgreens/cards/(?<segment>.*)", "/${segment}")
						.addResponseHeader("responseHeader", new Date().toString()))
						.uri("lb://CARDS")
						)
				.build();
}
}