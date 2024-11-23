package com.solomyuri.apigw.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;

@Component
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public ExceptionHandler(WebProperties webProperties, ApplicationContext applicationContext,
			ServerCodecConfigurer configurer) {
		super(new DefaultErrorAttributes(), webProperties.getResources(), applicationContext);
		this.setMessageWriters(configurer.getWriters());
		this.setMessageReaders(configurer.getReaders());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {

		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

		Throwable error = getError(request);
		HttpStatus httpStatus;
		LOGGER.error("An error has occurred", error);

		if (error instanceof ResponseStatusException responseStatusException)
			httpStatus = HttpStatus.valueOf(responseStatusException.getStatusCode().value());
		else
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		return ServerResponse.status(httpStatus)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(new ErrorResponse(httpStatus.value(), error.getMessage())));
	}

}
