package com.modeler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Metodo;
import com.modeler.repositories.MethodRepository;

@Service
public class MethodServices {
	@Autowired
	private MethodRepository repo;
	public void delete(Metodo m) throws ModelerException {
		if(repo.getOne(m.getId())==null) throw new ModelerException(ModelerException.noExisteMetodo);
		repo.delete(m);
	}
	public Metodo getModeloById(int id) {
		return repo.getOne(id);
	}
}
