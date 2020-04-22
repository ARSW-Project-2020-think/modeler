package com.modeler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Metodo;
import com.modeler.model.Rectangulo;
import com.modeler.repositories.MethodRepository;

@Service
public class MethodServices {
	@Autowired
	private MethodRepository repo;
	public void delete(Metodo m) throws ModelerException {
		if(repo.getOne(m.getId())==null) throw new ModelerException(ModelerException.noExisteMetodo);
		repo.delete(m);
	}
	public Metodo getMetodoById(int id) {
		return repo.getOne(id);
	}
	public void save(Metodo metodo, Rectangulo rectangulo) throws ModelerException {
		if(rectangulo.getMetodo(metodo.getMetodo())!=null) throw new ModelerException("Existe un metodo con este nombre");
		repo.save(metodo);
	}
}
