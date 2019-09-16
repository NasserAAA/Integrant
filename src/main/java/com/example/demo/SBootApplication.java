package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.demo.TwitterKafkaConsumer;


@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class SBootApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(SBootApplication.class, args);
	}
	
	@Bean
	public TwitterKafkaConsumer twitterConsumer() {
		return new TwitterKafkaConsumer();
	}
	

}
