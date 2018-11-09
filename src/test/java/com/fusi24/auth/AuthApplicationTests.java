package com.fusi24.auth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fusi24.auth.model.User;
import com.fusi24.auth.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {

	String USERNAME = "H1YP1";
	String PASSWORD = "H1YP1";
	
	@Autowired
	UserRepository userRepo;	
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void test_checkUsername() {
		User user = userRepo.findByUsernameAndPasswordAndIsActiveTrue(USERNAME, PASSWORD);
		Assert.assertTrue("Username is queried from database", user.getUsername().equals(USERNAME));
	}

}
