package com.modeler.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Version {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column
	private int numero;
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_proyecto")
	private Proyecto proyecto;
	@OneToMany(mappedBy="version")
	private List<Modelo> modelos;
	
	public Version() {
		
	}
	
	public Version(int numero) {
		this.numero = numero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	public boolean equals(Object ob) {
		if(!(ob instanceof Version)) return false;
		Version v = (Version) ob;
		return v.getId()==id;
	}
	
	public List<Modelo> getModelos() {
		return modelos;
	}

	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}
	
	public Modelo getModelo(String nombreModelo) {
		for(Modelo m:modelos) {
			if(m.getNombre().equals(nombreModelo)) return m;
		}
		return null;
	}

	public Rectangulo getRectangulo(String nombreModelo, String nombreRectangulo) {
		Modelo m = getModelo(nombreModelo);
		return null;
	}
	
}
