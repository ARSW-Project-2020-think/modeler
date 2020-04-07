package com.modeler.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value="Rectangulo")
public class Rectangulo extends Componente{

	@OneToMany(mappedBy="rectangulo",cascade=CascadeType.ALL)
	private List<Metodo>metodos=new ArrayList<Metodo>();
	@OneToMany(mappedBy="rectangulo",cascade=CascadeType.ALL)
	private List<Atributo> atributos=new ArrayList<Atributo>();

	public Rectangulo(){
		super();
	}

	public Rectangulo(String nombre,int x,int y,int ancho,int alto) {
		super(nombre,x,y,ancho,alto);
	}
	public Rectangulo(String nombre,int x,int y,int ancho,int alto,Modelo modelo) {
		super(nombre,x,y,ancho,alto,modelo);
	}

	public List<Metodo> getMetodos() {
		return metodos;
	}

	public void setMetodos(List<Metodo> metodos) {
		this.metodos = metodos;
	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	public void setAtributos(List<Atributo> atributos) {
		this.atributos = atributos;
	}
}
