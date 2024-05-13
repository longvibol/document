package com.in28minutes.learnspringsecurity.jwt;


import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class JwtAuthenticationResource {
	
	@PostMapping("/authenticate")
	public JwtResponse authenticate (Authentication authentication) {
		return new JwtResponse(createToken(authentication));
	}
	
	private String createToken(Authentication authentication) {
		// claims is the token return back befor encoder 
		// claims what are the authrity you have 
		var claims = JwtClaimsSet.builder()
			.issuer("self")
			.issuedAt(Instant.now())
			.expiresAt(Instant.now().plusSeconds(60*30))
			.subject(authentication.getName())
			.claim("scope", createScope(authentication))
			.build();
		
		return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

	// Create the Authority (rols of the user), if the user have many rols it will join with " "
	private String createScope(Authentication authentication) {
		return authentication.getAuthorities().stream()
		.map(a ->a.getAuthority())
		.collect(Collectors.joining(" "));	
	}

	// create JWT token 
	// 1 need Encoder (find resource to encode: username and password 
	
	private JwtEncoder jwtEncoder;
	
	public JwtAuthenticationResource(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}

}

// we want to return (JwtResponse) with a token back after we encode the username and password 
// we need the class

record JwtResponse(String token) {}
