package com.modeler.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Modelo implements Serializable {
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


	@OneToMany(mappedBy="modelo",cascade=CascadeType.ALL)
	private List<Componente> componentes;

	
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}

	public Componente getComponente(Componente ov) {
		for(Componente c: componentes) {
			if(c.equals(ov)) return c;
		}
		return null;
	}
	
	public Modelo clone() {
		Modelo m = new Modelo();
		ArrayList<Componente> cm = new ArrayList<>();
		for(Componente c: componentes) {
			Componente comp  = (Componente) c.clone(); 
			cm.add(comp);
			comp.setModelo(m);
		}
		return m;
	}
	
}
