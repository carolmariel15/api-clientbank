package com.nttdata.apliclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ApiClientbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiClientbankApplication.class, args);
	}

}
