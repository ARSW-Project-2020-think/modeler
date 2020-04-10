package com.modeler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Metodo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_metodo")
    private Rectangulo rectangulo;

    private String metodo;
    
    public Metodo() {
    	
    }
    
    public Metodo(String metodo) {
    	this.metodo = metodo;
    }
    
    public Metodo(String metodo,Rectangulo rectangulo) {
    	this(metodo);
    	this.rectangulo = rectangulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rectangulo getRectangulo() {
        return rectangulo;
    }

    public void setRectangulo(Rectangulo rectangulo) {
        this.rectangulo = rectangulo;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
