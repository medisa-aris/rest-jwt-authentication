package com.fusi24.auth.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusi24.auth.model.JwtResponse;
import com.fusi24.auth.model.JwtResponse.Status;
import com.fusi24.auth.model.User;
import com.fusi24.auth.model.UserRequest;
import com.fusi24.auth.service.SecretService;
import com.fusi24.auth.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class FusiAuthController extends BaseController {
	Logger log = Logger.getLogger(FusiAuthController.class);

	@Autowired
	SecretService secretService;
	@Autowired
	UserService userService;

	@PostMapping(value = "/create-token")
	public JwtResponse createToken(@RequestBody Map<String, Object> claims) throws UnsupportedEncodingException {
		String jws = Jwts.builder().setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, secretService.getHS256SecretBytes()).compact();
		return new JwtResponse(jws);
	}

	@GetMapping(value = "/check-token")
	public JwtResponse checkToken(@RequestParam String jwt) throws UnsupportedEncodingException {
		Jws<Claims> jws = Jwts.parser().setSigningKeyResolver(secretService.getSigningKeyResolver())
				.parseClaimsJws(jwt);

		return new JwtResponse(jws);
	}
	
	@PostMapping(value = "/auth")
	public JwtResponse authentication(@RequestBody UserRequest userReq) throws IllegalAccessException, UnsupportedEncodingException {
		User user = userService.getUser(userReq.getUsername(), userReq.getPassword());
		if(user != null) {
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("username", user.getUsername());
			claims.put("email", user.getEmail());
			claims.put("id", user.getId());
			claims.put("idKaryawan", user.getIdKaryawan());
			return createToken(claims);
		}
		
		JwtResponse res = new JwtResponse();
		res.setStatus(Status.ERROR);
		res.setMessage("Login failed. Please check username or password.");
		res.setExceptionType("N/A");
		return res;
		
	}
	
	@RequestMapping(value = "/token-error")
	public JwtResponse error() {
		JwtResponse res = new JwtResponse();
		res.setExceptionType("N/A");
		res.setStatus(Status.ERROR);
		res.setMessage("Credential not match.");
		
		return res;
	}
}
