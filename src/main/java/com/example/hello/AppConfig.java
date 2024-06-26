package com.example.hello;

import java.util.List;
import java.util.function.Predicate;

import am.ik.accesslogger.AccessLogger;
import am.ik.accesslogger.AccessLoggerBuilder;

import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

	@Bean
	public AccessLogger accessLogger() {
		Predicate<HttpExchange> excludeActuator = httpExchange -> {
			String uri = httpExchange.getRequest().getUri().getPath();
			return uri != null && !(uri.equals("/readyz") || uri.equals("/livez") || uri.startsWith("/actuator"));
		};
		Predicate<HttpExchange> excludeRoute53 = httpExchange -> {
			String userAgent = CollectionUtils.firstElement(httpExchange.getRequest().getHeaders().get("user-agent"));
			return userAgent != null && !userAgent.startsWith("Amazon-Route53-Health-Check-Service");
		};
		return AccessLoggerBuilder.accessLogger().filter(excludeRoute53.and(excludeActuator)).build();
	}

}
