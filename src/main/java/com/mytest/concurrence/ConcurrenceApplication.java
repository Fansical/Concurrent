package com.mytest.concurrence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcurrenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurrenceApplication.class, args);
		System.out.println("Hello!");
	}
}
