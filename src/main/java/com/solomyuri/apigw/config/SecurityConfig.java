package com.solomyuri.apigw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	private final String[] permitList = { "/api/sso/auth/token", "/api/sso/auth/refresh", "/api/sso/users" };

	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {

		http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
		http.authorizeExchange(
				auth -> auth.pathMatchers(HttpMethod.POST, permitList).permitAll().anyExchange().authenticated());
		http.csrf(ServerHttpSecurity.CsrfSpec::disable);

		return http.build();
	}

}
