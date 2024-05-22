package com.example.hello;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloWorldTanzuApplicationTests {

	@Autowired
	RestClient.Builder restClientBuilder;

	RestClient restClient;

	@LocalServerPort
	int port;

	@BeforeEach
	public void init() {
		if (this.restClient == null) {
			this.restClient = this.restClientBuilder.baseUrl("http://localhost:" + this.port).build();
		}
	}

	@Test
	void contextLoads() {
		ResponseEntity<String> response = this.restClient.get().uri("/").retrieve().toEntity(String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("Hello Tanzu!");
	}

}
