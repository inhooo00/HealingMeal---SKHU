package com.example.thehealingmeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TheHealingMealApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheHealingMealApplication.class, args);
	}

}
