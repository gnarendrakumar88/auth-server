package com.shoppy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shoppy.dao.UsersRepo;
import com.shoppy.model.Users;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepo usersRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Users user= usersRepo.findByUsername(username);
		
		if (user==null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("User Not Found");
		}
		return user;
		
	}
	
	public Users getUser(String username) {
		return usersRepo.findByUsername(username);
	}

}
