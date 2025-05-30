package com.cse5382.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.cse5382.assignment.Config.AuthUserProperties;

@SpringBootApplication
@ComponentScan({"com.cse5382.assignment"})
@EnableConfigurationProperties(AuthUserProperties.class)
public class AssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentApplication.class, args);
	}

}
