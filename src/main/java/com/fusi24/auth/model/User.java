package com.fusi24.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_user")
public class User {
	@Id
	private Long id;
	private String username;
	private String password;
	@Column(name="id_karyawan")
	private Long idKaryawan;
	private String selular;
	private String email;
	@Column(name="is_active")
	private Boolean isActive;
	@Column(name="password_hash")
	private String passwordHash;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getIdKaryawan() {
		return idKaryawan;
	}
	public void setIdKaryawan(Long idKaryawan) {
		this.idKaryawan = idKaryawan;
	}
	public String getSelular() {
		return selular;
	}
	public void setSelular(String selular) {
		this.selular = selular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
