package com.solomyuri.apigw.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogFilter implements WebFilter {

	private final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

		String pstxid = UUID.randomUUID().toString();

		ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("pstxid", pstxid).build();

		ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

		LOGGER.info("Request: method={}, uri={}, headers={}", modifiedRequest.getMethod(), modifiedRequest.getURI(),
				modifiedRequest.getHeaders());

		return chain.filter(modifiedExchange)
				.doOnTerminate(() -> LOGGER.info("Response: status={}, pstxid={}",
						exchange.getResponse().getStatusCode(), pstxid));
	}
}
