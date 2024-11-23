package com.solomyuri.apigw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class AuthErrorHandler implements ServerAuthenticationEntryPoint {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {

		ErrorResponse errorResponse = new ErrorResponse(401, ex.getMessage());
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

		try {
			byte[] responseBody = objectMapper.writeValueAsBytes(errorResponse);
			return exchange.getResponse()
					.writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(responseBody)));
		} catch (Exception e) {
			return Mono.error(e);
		}
	}

}