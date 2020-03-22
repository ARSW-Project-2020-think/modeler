package com.modeler.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modeler.exceptions.ModelerException;

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
	@JsonIgnore
	@OneToMany(mappedBy="autor")
	private Set<Proyecto> proyectos;
	

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
	
	public Set<Proyecto> getProyectos() {
		System.out.println("Obtiene proyectos");
		return proyectos;
	}
	
	public void setProyectos(Set<Proyecto> proyectos) {
		System.out.println("Actualiza proyectos");
		this.proyectos = proyectos;
	}
	
	public void addProyecto(Proyecto proyecto) throws ModelerException {
		for(Proyecto p:getProyectos()) {
			if(p.equals(proyecto)) throw new ModelerException(ModelerException.nombreProyecto);
		}
		proyectos.add(proyecto);
	}
	public boolean equals(Object obj) {
		if(!(obj instanceof Usuario)) return false;
		return ((Usuario) obj).getCorreo().equals(correo);
	}
	public Proyecto getProyectoByName(String nombre) {
		for(Proyecto p:proyectos) {
			if(p.getNombre().equals(nombre)) return p;
		}
		return null;
	}

	
}
