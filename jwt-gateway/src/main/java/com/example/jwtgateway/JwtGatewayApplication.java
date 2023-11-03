package com.example.jwtgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JwtGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtGatewayApplication.class, args);
	}

}
