package com.modeler.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="compartido",
	joinColumns=@JoinColumn(name="id_usuario"),
	inverseJoinColumns=@JoinColumn(name="id_proyecto"))
	private List<Proyecto> proyectosCompartidos;
	

	public Usuario() {
		
	}
	public Usuario(String correo,String username,String password) {
		this.correo = correo;
		this.username = username;
		this.password = password;
		this.proyectosCompartidos=new ArrayList<Proyecto>();
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
	
	private Proyecto getProyecto(String nombreProyecto) {
		for(Proyecto p:proyectos) {
			if(p.getNombre().equals(nombreProyecto)) return p;
		}
		return null;
	}
	
	public Version getVersion(String nombreProyecto, int version) {
		Proyecto proyecto = getProyecto(nombreProyecto);
		return proyecto.getVersion(version);
	}
	
	public Modelo getModelo(String nombreProyecto, int version, String nombreModelo) {
		Proyecto proyecto = getProyecto(nombreProyecto);
		return proyecto.getModelo(version,nombreModelo);
	}
	
	public List<Modelo> getModelos(String nombreProyecto, int version) {
		Proyecto proyecto = getProyecto(nombreProyecto);
		System.out.println(proyectos);
		return proyecto.getModelos(version);
	}
	
	public Rectangulo getRectangulo(String nombreProyecto, int version, String nombreModelo, String nombreRectangulo) {
		Proyecto p = getProyecto(nombreProyecto);
		return p.getRectangulo(version,nombreModelo,nombreRectangulo);
	}
	public List<Proyecto> getProyectosCompartidos() {
		return proyectosCompartidos;
	}
	public void setProyectosCompartidos(List<Proyecto> proyectosCompartidos) {
		this.proyectosCompartidos = proyectosCompartidos;
	}
	private boolean isShareProyecto(Proyecto p) {
		for(Proyecto pr: proyectosCompartidos) {
			if(p.getId()==pr.getId()) return true;
		}
		return false;
	}
	public void addProyectoCompartido(Proyecto p) throws ModelerException{
		if(isShareProyecto(p)) throw new ModelerException(ModelerException.proyectoYaCompartido);
		proyectosCompartidos.add(p);
		p.addColaborador(this);
	}
	

	
}
