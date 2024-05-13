package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}
	
	// http://localhost:3000/ to 5000
	// Cross Origin Requests
	// Allow all requests only from http://localhost:300
	// we need to overight method call: addCorsMappings(in WMV) 
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				
				registry.addMapping("/**") // allow all path partern /** mean allow every thing
					.allowedMethods("*") // allow method : get, put, post .... 
					.allowedOrigins("http://localhost:3000/"); // where we put the source comming from 
			}
		};
	}
	
	

}
