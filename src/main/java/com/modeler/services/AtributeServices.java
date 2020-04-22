package com.modeler.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Atributo;
import com.modeler.model.Rectangulo;
import com.modeler.repositories.AtributeRepository;

@Service
public class AtributeServices {
	@Autowired
	private AtributeRepository repo;
	public void save(Atributo at,Rectangulo r) throws ModelerException {
		if(r.getAtributo(at.getAtributo())!=null) throw new ModelerException(ModelerException.atributoInvalido);
		repo.save(at);
	}
}
