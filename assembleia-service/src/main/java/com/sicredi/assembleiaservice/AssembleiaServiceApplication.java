package com.sicredi.assembleiaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AssembleiaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssembleiaServiceApplication.class, args);
	}

}
