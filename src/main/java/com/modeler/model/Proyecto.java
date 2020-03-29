package com.modeler.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "usuario")
	private Usuario autor;
	
	
	@OneToMany(mappedBy="proyecto")
	private List<Version> versiones;
	
	
	@JsonIgnore
	@ManyToMany(mappedBy="proyectosCompartidos",fetch = FetchType.LAZY)
	private List<Usuario> colaboradores;
	public Proyecto() {
		
	}
	public Proyecto(int id, String nombre, boolean publico, Usuario usuario) {
		this(nombre,publico,usuario);
		this.id = id;
	}
	public Proyecto(String nombre, boolean publico, Usuario usuario) {
		this.nombre = nombre;
		this.publico = publico;
		this.autor = usuario;
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
	
	public Usuario getAutor() {
		return autor;
	}
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	public List<Version> getVersiones() {
		return versiones;
	}
	public void setVersiones(List<Version> versiones) {
		this.versiones = versiones;
	}
	public boolean equals(Object obj) {
		if(!(obj instanceof Proyecto)) return false;
		Proyecto p = ((Proyecto) obj);
		return p.getId()==id;
	}
	
	public String toString() {
		return getNombre()+" "+getId()+" "+publico;
	}
	public Version getVersion(int version) {
		for(Version v:versiones) {
			if(v.getNumero()==version) return v;
		}
		return null;
	}
	public Modelo getModelo(int version, String nombreModelo) {
		Version v = getVersion(version);
		return v.getModelo(nombreModelo);
	}
	public List<Modelo> getModelos(int version) {
		Version v = getVersion(version);
		return v.getModelos();
	}
	public Rectangulo getRectangulo(int version, String nombreModelo, String nombreRectangulo) {
		Version v = getVersion(version);
		return v.getRectangulo(nombreModelo,nombreRectangulo);
	}
	public List<Usuario> getColaboradores() {
		return colaboradores;
	}
	public void setColaboradores(List<Usuario> colaboradores) {
		this.colaboradores = colaboradores;
	}
	public void addColaborador(Usuario usuario) {
		colaboradores.add(usuario);
		
	}
	
}
