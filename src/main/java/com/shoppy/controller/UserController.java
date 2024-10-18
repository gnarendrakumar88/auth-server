package com.shoppy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppy.dto.LoginRequest;
import com.shoppy.service.JwtService;

@RestController
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/log")
	public String login(@RequestBody LoginRequest request){

		//AuthenticationManager -> Authentication Provider (Security Config class) -> DaoAuthenticationProvider 
		//	-> UserDetailsService -> Database Call to retrieve user details.
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		if(authentication.isAuthenticated())
			return jwtService.generateToken(request.getUsername());
		else
			throw new RuntimeException("Login Failed");

	}
	
	@GetMapping("/hi")
	public String hi() {
		return "Hi";
	}
	
}
