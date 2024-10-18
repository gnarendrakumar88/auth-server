package com.shoppy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoppy.model.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long>{
	
	Users findByUsername(String username);

}
