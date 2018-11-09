package com.fusi24.auth;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fusi24.auth.model.Config;
import com.fusi24.auth.repository.ConfigRepository;
import com.fusi24.auth.service.SecretService;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {
	Logger log = Logger.getLogger(AuthApplication.class);

	public static String JWT_SECRETS_CONFIG_KEY = "JWT_SECRETS";
	
	@Autowired
	ConfigRepository configRepo;
	@Autowired
	SecretService secretService;
	@Autowired
	Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Config config = configRepo.findOneByKey(JWT_SECRETS_CONFIG_KEY);
		
		if(config == null)
			log.error("JWT_SECRETS Config NOT shown on database.");
		else {
			HashMap<String,String> result =
			        new ObjectMapper().readValue(config.getValue(), HashMap.class);
			secretService.setSecrets(result);
			
			log.info("Successfully set JWT_SECRETS. Secrets are sync.");
		}
	}
}
