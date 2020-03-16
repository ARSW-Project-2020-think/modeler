package com.modeler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "usuario")
	private Usuario usuario;
	
	public Proyecto() {
		
	}
	public Proyecto(int id, String nombre, boolean publico, Usuario usuario) {
		this(nombre,publico,usuario);
		this.id = id;
	}
	public Proyecto(String nombre, boolean publico, Usuario usuario) {
		this.nombre = nombre;
		this.publico = publico;
		this.usuario = usuario;
	}
	public Proyecto(String nombre, boolean publico) {
		this.nombre = nombre;
		this.publico = publico;
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


	public boolean getPublico() {
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
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Proyecto)) return false;
		Proyecto p = ((Proyecto) obj);
		return p.getNombre().equals(nombre) && p.getUsuario().equals(usuario);
	}
	
	public String toString() {
		return getNombre()+" "+getId()+" "+publico;
	}
}
