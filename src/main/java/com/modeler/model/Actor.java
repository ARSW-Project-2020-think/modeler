package com.modeler.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue(value="Actor")
public class Actor extends Componente implements Serializable {
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
    public int hashCode() {
    	return 12;
    }
	
	@Override
	public Object clone() {
		return new Actor(super.getNombre(),super.getX(),super.getY(),super.getAncho(),super.getAlto(),super.getModelo());
	}
	
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof Actor)) return false;
		return super.equals(ob) || ((Actor) ob).getNombre().equals(super.getNombre());
	}
}
