package com.example.myspringbootapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MySpringbootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringbootAppApplication.class, args);
	}

}
