package com.modeler.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="Ovalo")
public class Ovalo extends Componente {
		
    public Ovalo() {
    }
    
    public Ovalo(int x, int y) {
		super.setX(x);
		super.setY(y);
	}

	public Ovalo(int x, int y, Modelo m) {
		this(x,y);
		super.setModelo(m);
	}

	public Ovalo(String nombre, int x, int y, Modelo modelo) {
		this(x,y,modelo);
		setNombre(nombre);
	}

	@Override
    public int hashCode() {
    	return 8;
    }
	@Override
	public boolean equals(Object ob) {
		if(!(ob instanceof Ovalo)) return false;
		return super.equals(ob) || super.getNombre().equals(((Ovalo) ob).getNombre());
	}
	
	@Override
	public Object clone() {
		return new Ovalo(super.getNombre(),super.getX(),super.getY(),super.getModelo());
	}
}
