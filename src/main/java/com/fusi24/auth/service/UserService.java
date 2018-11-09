package com.fusi24.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusi24.auth.model.User;
import com.fusi24.auth.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	
	public Boolean checkAccount(String username, String password) {
		User user = userRepo.findByUsernameAndPasswordAndIsActiveTrue(username, password);
		if(user != null)
			if(user.getUsername().equals(username))
				return true;
		
		return false;
	}
	
	public User getUser(String username, String password) {
		return userRepo.findByUsernameAndPasswordAndIsActiveTrue(username, password);
	}
}
