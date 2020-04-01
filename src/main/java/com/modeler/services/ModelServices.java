package com.modeler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Modelo;
import com.modeler.repositories.ModelRepository;

@Service
public class ModelServices {
	
	@Autowired
	private ModelRepository repo;
	
	
	public void save(Modelo modelo) throws ModelerException{
		for(Modelo m : repo.getModelosByName(modelo.getNombre())) {
			if(m.getVersion().equals(modelo.getVersion()))
				throw new ModelerException(ModelerException.modeloInvalido);
		}
		repo.save(modelo);
	}


	public Modelo getModelById(int id) {
		return repo.findOne(id);
		
	}
	
	
}
