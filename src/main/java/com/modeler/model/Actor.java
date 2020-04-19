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
}
