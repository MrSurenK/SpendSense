package com.MrSurenK.SpendSense_BackEnd;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpendSenseBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpendSenseBackEndApplication.class, args);
	}
}
//ToDo: Fix double transaction table appearing in mysql db