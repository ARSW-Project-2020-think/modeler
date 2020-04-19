package com.modeler.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Actor")
public class Actor extends Componente{
	public Actor() {
		
	}
	
	public Actor(String nombre, int x, int y,int ancho,int alto) {
		super(nombre,x,y,ancho,alto);
	}
	public Actor(String nombre, int x, int y,int ancho,int alto,Modelo modelo) {
		this(nombre,x,y,ancho,alto);
		setModelo(modelo);
	}
	
	@Override
	public Object clone() {
		return new Actor(super.getNombre(),super.getX(),super.getY(),super.getAncho(),super.getAlto(),super.getModelo());
	}
}
