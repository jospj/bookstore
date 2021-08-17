package com.online.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
public class BookStoreServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreServicesApplication.class, args);
	}
}
