package com.modeler.model;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.modeler.exceptions.ModelerException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value="Rectangulo")
public class Rectangulo extends Componente implements Serializable {
	@OneToMany(mappedBy="rectangulo",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Metodo>metodos=new ArrayList<Metodo>();
	@LazyCollection(LazyCollectionOption.FALSE)
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
	
	@Override
	public int hashCode() {
		return 7;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Rectangulo)) return false;
		return super.equals(obj) || super.getNombre().equals(((Rectangulo) obj).getNombre());
	}
	
	public Metodo getMetodo(String text) {
		for(Metodo m:metodos) {
			if(m.getMetodo().equals(text)) return m;
		}
		return null;
	}
	public void addMetodo(Metodo metodo) throws ModelerException {
		if(getMetodo(metodo.getMetodo())!=null) throw new ModelerException(ModelerException.metodoExistente);
		metodos.add(metodo);
	}
	@Override
	public Object clone() {
		Rectangulo r = new Rectangulo(super.getNombre(),super.getX(),super.getY(),super.getAncho(),super.getAlto(),super.getModelo());
		ArrayList<Atributo> atr = new ArrayList<Atributo>();
		ArrayList<Metodo> mt = new ArrayList<Metodo>();
		for(Atributo a:atributos) {
			Atributo b = a.clone();
			b.setRectangulo(r);
			atr.add(b); 
		}
		for(Metodo m:metodos) {
			Metodo m2 = m.clone();
			mt.add(m2); 
			m2.setRectangulo(r);
		}
		return r;
		
	}
	public Atributo getAtributo(String atributo) {
		for(Atributo a: atributos) {
			if(a.getAtributo().equals(atributo)) return a;
		}
		return null;
	}
	public void addAtributo(Atributo atributo) throws ModelerException {
		if(getAtributo(atributo.getAtributo())!=null) throw new ModelerException(ModelerException.atributoInvalido);
		atributos.add(atributo);
		
	}
}
