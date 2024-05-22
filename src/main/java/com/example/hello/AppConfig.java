package com.example.hello;

import am.ik.accesslogger.AccessLogger;
import am.ik.accesslogger.AccessLoggerBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

	@Bean
	public AccessLogger accessLogger() {
		return AccessLoggerBuilder.accessLogger().filter(httpExchange -> {
			String uri = httpExchange.getRequest().getUri().getPath();
			return uri != null && !(uri.equals("/readyz") || uri.equals("/livez") || uri.startsWith("/actuator"));
		}).build();
	}

}
