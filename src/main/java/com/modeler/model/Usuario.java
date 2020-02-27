package com.modeler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {
	@Id
	@Column(name="email")
	private String email;
}
