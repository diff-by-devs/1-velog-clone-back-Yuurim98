package com.diffbydevs.velog_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class VelogCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(VelogCloneApplication.class, args);
	}

}
