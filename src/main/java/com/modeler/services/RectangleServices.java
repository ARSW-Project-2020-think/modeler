package com.modeler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Rectangulo;
import com.modeler.repositories.RectangleRepository;

@Service
public class RectangleServices {
	@Autowired
	private RectangleRepository repo;
	
	public void save(Rectangulo rectangulo) throws ModelerException{
		for(Rectangulo r: repo.getRectanglesByName(rectangulo.getNombre())) {
			if(rectangulo.getModelo().equals(r.getModelo())) throw new ModelerException(ModelerException.claseInvalida);
		}
		repo.save(rectangulo);
	}

	public void update(Rectangulo m) throws ModelerException {
		if(repo.getOne(m.getId())==null) throw new ModelerException(ModelerException.claseInexistente);
		repo.save(m);
		
	}

	public Rectangulo getRectangleById(int id) {
		return repo.findById(id).orElse(null);
		
	}
}
