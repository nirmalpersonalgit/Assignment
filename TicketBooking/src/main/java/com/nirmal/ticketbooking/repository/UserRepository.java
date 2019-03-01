package com.nirmal.ticketbooking.repository;

import org.springframework.data.repository.CrudRepository;
import com.nirmal.ticketbooking.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {	
	
	public User findByUsernameAndPassword(String username, String password);
	
}
