package com.web.rantbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.web.rantbuddy")
public class RantbuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RantbuddyApplication.class, args);
	}

}
