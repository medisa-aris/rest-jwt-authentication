package com.fusi24.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusi24.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsernameAndPasswordAndIsActiveTrue(String username, String password);
}
