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

import java.io.Serializable;

@Entity
@Table
public class Linea implements Serializable {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int x1;
	
	@Column
	private int y1;
	
	@Column
	private int x2;
	
	@Column
	private int y2;
	
	@Column
	private String cardinal1;
	
	@Column
	private String cardinal2;
	
	@Column
	private String nombre1;
	
	@Column
	private String nombre2;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_modelo")
	private Modelo modelo;
	
	public Linea() {
		
	}
	
	public Linea(int x1,int y1,int x2,int y2) {
		this.x1=x1;
		this.y1=y1;
		this.x2=x2;
		this.y2=y2;
	}
	
	public Linea(int x1,int y1,int x2,int y2,String cardinal1,String cardinal2) {
		this(x1,y1,x2,y2);
		this.cardinal1 = cardinal1;
		this.cardinal2 = cardinal2;
	}
	
	public Linea(int x1,int y1,int x2,int y2,String cardinal1,String cardinal2,String nombre1,String nombre2) {
		this(x1,y1,x2,y2,cardinal1,cardinal2);
		this.nombre1=nombre1;
		this.nombre2=nombre2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public String getCardinal1() {
		return cardinal1;
	}

	public void setCardinal1(String cardinal1) {
		this.cardinal1 = cardinal1;
	}

	public String getCardinal2() {
		return cardinal2;
	}

	public void setCardinal2(String cardinal2) {
		this.cardinal2 = cardinal2;
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof Linea)) return false;
		Linea linea = (Linea) obj;
		return linea.getNombre1().equals(nombre1) && linea.getNombre2().equals(nombre2);
	}
	
	
	
}
