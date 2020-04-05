package com.modeler.model;

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
	
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="modelo",cascade=CascadeType.ALL)
	private List<Rectangulo> rectangulos;
	
	@OneToMany(mappedBy="modelo")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Linea> lineas;
	
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

	public List<Linea> getLineas() {
		return lineas;
	}

	public void setLineas(List<Linea> lineas) {
		this.lineas = lineas;
	}

	public Linea getLinea(Linea linea) {
		for(Linea l: lineas) {
			if(l.getX1()==linea.getX1() && l.getY1()==linea.getY1() && l.getY2()==linea.getY2() && linea.getX2()==l.getX2()
					&& l.getNombre1().equals(linea.getNombre1()) && l.getNombre2().equals(linea.getNombre2()))
				return l;
		}
		return null;
	}
	
	public Linea getLineaById(int id) {
		for(Linea l:lineas) {
			if(l.getId()==id) return l;
		}
		return null;
	}
	
}
