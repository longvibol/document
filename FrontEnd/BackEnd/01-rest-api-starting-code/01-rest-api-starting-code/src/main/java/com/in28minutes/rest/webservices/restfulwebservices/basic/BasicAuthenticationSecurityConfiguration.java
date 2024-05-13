package com.in28minutes.rest.webservices.restfulwebservices.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthenticationSecurityConfiguration {
	
	//1. Filter Chain : mean when the request come to security it will check again the filter 
	// 1-> we need to defind the filter you want to filter check  ( we want to customize filter chin ) 
	// 2. filter will authenticated all the request 
	// *3. we want : basic authenticated by Disabling CSRF
	// 4. in order to make section (we don't need section ) it call statess rest api 	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 1: Response to perflight request doesn't pass access control check 
		// 2: basic auth by check the token from the user when login 
		return 
				http
					.authorizeHttpRequests(
						auth -> 
							auth
							// if and option request come in to any of url - > we will allow 
							.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
							.anyRequest().authenticated())
					.httpBasic(Customizer.withDefaults())		
					.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.csrf().disable().build();		
		
		
		
//		http.authorizeHttpRequests(
//				auth -> auth.anyRequest().authenticated()
//				);
//		
//		// Add basic authenticatin : it have username and password 
//		http.httpBasic(Customizer.withDefaults());
//		
//		//configic Statless Api call 
//		
//		http.sessionManagement(
//				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		
//		// disable csrf
//		http.csrf().disable();
//		
//		return http.build();
	}

}
