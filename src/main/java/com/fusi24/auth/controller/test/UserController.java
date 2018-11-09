package com.fusi24.auth.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusi24.auth.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/acc")
	public Boolean checkAccount(@RequestParam String user,@RequestParam String pass) {
		return userService.checkAccount(user, pass);
	}
}
