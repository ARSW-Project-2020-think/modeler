package com.modeler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Proyecto {
	
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nombre;
	
	@Column
	private boolean publico;
	
	@ManyToOne
	@JoinColumn(name = "usuario")
	private Usuario usuario;
	
	
	public Proyecto(int id, String nombre, boolean publico, Usuario usuario) {
		this.id = id;
		this.nombre = nombre;
		this.publico = publico;
		this.usuario = usuario;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public boolean isPublico() {
		return publico;
	}


	public void setPublico(boolean publico) {
		this.publico = publico;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
