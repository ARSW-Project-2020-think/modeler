package com.modeler.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Proyecto;
import com.modeler.model.Usuario;
import com.modeler.repositories.ProjectRepository;

@Service
public class ProjectServices {
	
	@Autowired
	private ProjectRepository repositorio;
	
	public void add(Proyecto proyecto) throws ModelerException {
		Usuario u = proyecto.getAutor();
		for (Proyecto p: repositorio.getProyectoByusuario(u.getCorreo())) {
			if (p.getNombre().equals(proyecto.getNombre())) {
				throw new ModelerException(ModelerException.nombreProyecto);
			}
		}		
		repositorio.save(proyecto);
	}
	
	
	
	
	public List<Proyecto> getAll() {
		return repositorio.findAll();
	}
}
