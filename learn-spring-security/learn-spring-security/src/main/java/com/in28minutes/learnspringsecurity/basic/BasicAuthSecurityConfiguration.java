package com.in28minutes.learnspringsecurity.basic;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class BasicAuthSecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> {
						auth
							.requestMatchers("/users").hasRole("USER")
							.requestMatchers("/admin/**").hasRole("ADMIN")
							.anyRequest()
							.authenticated();
						
						
			// main any request show be authenticated
		});
		// disable session policy
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));		
		http.httpBasic();
		http.csrf().disable();		
		http.headers().frameOptions().sameOrigin();		
		
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {		
//		var user = User.withUsername("vibol")
//			.password("{noop}123")
//			// noop refer to Encrip code 
//			.roles("USER")
//			.build();		
//		var admin = User.withUsername("admin")
//				.password("{noop}123")
//				// noop refer to Encrip code 
//				.roles("ADMIN")
//				.build();		
//		return new InMemoryUserDetailsManager(user, admin);
//	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)				
				// this script we get from jdbcDaoSupport -> DEFAULT_USER_SCHEMA_DDL_LOCATION
				// need this script to crete user in the h2 database to create user schema 
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	// create user in he the database local memonry h2
	@Bean
	
	public UserDetailsService userDetailsService(DataSource dataSource) {	
		
		var user = User.withUsername("vibol")
			//.password("{noop}123")
			// noop refer to Encrip code 
			.password("123")
			.passwordEncoder(str -> passwordEncoder().encode(str))
			.roles("USER")
			.build();		
		var admin = User.withUsername("admin")
				//.password("{noop}123")
				// noop refer to Encrip code 				
				.password("123")
				.passwordEncoder(str -> passwordEncoder().encode(str))
				.roles("ADMIN","USER")
				.build();
		
		var jdbcUserDetailsManager = new JdbcUserDetailsManager((DataSource) dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);
		
		return jdbcUserDetailsManager;
	}
	
	
	// ceate hashing password by Bcrypte method have one way function 
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
}




























