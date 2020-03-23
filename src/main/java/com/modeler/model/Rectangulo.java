package com.modeler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Rectangulo {
	
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nombre;
	
	@Column
	private int x;
	
	@Column
	private int y;
	
	@Column
	private int ancho;
	
	@Column
	private int alto;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_modelo")
	private Modelo modelo;
	
	public Rectangulo() {
		
	}
	
	public Rectangulo(String nombre,int x,int y,int ancho,int alto) {
		this.nombre = nombre;
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
	}
	public Rectangulo(String nombre,int x,int y,int ancho,int alto,Modelo modelo) {
		this(nombre,x,y,ancho,alto);
		this.modelo = modelo;
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	
	public boolean equals(Object ob) {
		if(!(ob instanceof Rectangulo)) return false;
		return ((Rectangulo) ob).getId() ==id;
	}
}
