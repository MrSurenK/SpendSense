package com.MrSurenK.SpendCentsBackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpendCentsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpendCentsBackEndApplication.class, args);
	}
}
