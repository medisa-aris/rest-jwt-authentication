package com.fusi24.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusi24.auth.model.Config;

public interface ConfigRepository extends JpaRepository<Config, Long> {
	Config findOneByKey(String key);
}
