package com.actions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class ActionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActionsApplication.class, args);
	}

} 


  