package com.bridgelabz.eurekaServerNoteService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RibbonClient(name="forex-service")
public class ToDoEurekaNoteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoEurekaNoteServiceApplication.class, args);
	}
}
