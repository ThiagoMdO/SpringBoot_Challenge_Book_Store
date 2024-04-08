package com.thiagomdo.ba.challenge.msfeedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsFeedbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsFeedbackApplication.class, args);
	}

}
