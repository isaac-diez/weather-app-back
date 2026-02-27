package com.isaac.weatherapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class WeatherappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherappApplication.class, args);
	}

}
