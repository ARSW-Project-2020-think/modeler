package com.modeler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Usuario {
	@Id
	@Column
	private String correo;
	@Column
	private String username;
	@Column
	private String password;
	public Usuario() {
		
	}
	public Usuario(String correo,String username,String password) {
		this.correo = correo;
		this.username = username;
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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
	@Override
	public Usuario clone() {
		return new Usuario(correo,username,null);
	}
	
	
}
