package com.lendico.repayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class PlanGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanGeneratorApplication.class, args);
	}

}
