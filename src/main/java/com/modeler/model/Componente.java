package com.modeler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.modeler.exceptions.ModelerException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorColumn(name = "tipo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public abstract class Componente {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private int x;

    private int y;

    private int ancho;

    private int alto;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_modelo")
    private Modelo modelo;
    
    @JsonIgnoreProperties("relaciones")
    @ManyToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
    @JoinTable(name="Relacion",
            joinColumns={@JoinColumn(name="componente_id")},
            inverseJoinColumns={@JoinColumn(name="componente2_id")})
    private Set<Componente> relaciones = new HashSet<Componente>();
    @JsonIgnore
    @ManyToMany(mappedBy="relaciones",fetch=FetchType.LAZY)
    private Set<Componente> componentesRelacionados = new HashSet<Componente>();

    public Componente(){
    }

    public Componente(String nombre,int x,int y,int ancho,int alto) {
        super();
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }
    public Componente(String nombre,int x,int y,int ancho,int alto,Modelo modelo) {
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

    public Set<Componente> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(Set<Componente> relaciones) {
        this.relaciones = relaciones;
    }

    public Set<Componente> getComponentesRelacionados() {
        return componentesRelacionados;
    }

    public void setComponentesRelacionados(Set<Componente> componentesRelacionados) {
        this.componentesRelacionados = componentesRelacionados;
    }
    
    public Componente getComponente(Componente componente) {
    	for(Componente c:relaciones) {
    		if(c.equals(componente)) return c;
    	}
    	return null;
    }
    
    public void addComponente(Componente componente) throws ModelerException{
    	if(getComponente(componente)!=null) throw new ModelerException("Ya existe una relacion con este componente");
    	relaciones.add(componente);
    }
    
    public void removerComponenteRelacion(Componente componente) {
    	if(getComponente(componente)!=null) {
    		relaciones.remove(componente);
    	}
    }
    
    public boolean equals(Object ob) {
    	if(!(ob instanceof Componente)) return false;
    	return ((Componente)ob).getId() == id;
    }
}
