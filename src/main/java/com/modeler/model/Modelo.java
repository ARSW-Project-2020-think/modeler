package com.modeler.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Modelo {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nombre;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_version")
	private Version version;
	
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="modelo")
	private List<Rectangulo> rectangulos;
	
	@Column
	private String tipo;
	
	public Modelo() {
		
	}
	
	public Modelo(String nombre,Version version) {
		this.nombre = nombre;
		this.version = version;
	}
	
	public Modelo(String nombre, Version version, String tipo) {
		this(nombre,version);
		this.tipo = tipo;
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
	
	public Version getVersion() {
		return version;
	}
	
	public void setVersion(Version version) {
		this.version = version;
	}
	
	public boolean equals(Object ob) {
		if(!(ob instanceof Modelo)) return false;
		Modelo m = (Modelo) ob;
		return m.getId()==id;
	}

	public Rectangulo getRectangulo(String nombreRectangulo) {
		for(Rectangulo r: rectangulos) {
			if(r.getNombre().equals(nombreRectangulo)) return r;
		}
		return null;
	}

	public List<Rectangulo> getRectangulos() {
		return rectangulos;
	}

	public void setRectangulos(List<Rectangulo> rectangulos) {
		this.rectangulos = rectangulos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void addRectangulo(Rectangulo rectangulo) {
		rectangulos.add(rectangulo);
		rectangulo.setModelo(this);
		
	}
	
	
}
