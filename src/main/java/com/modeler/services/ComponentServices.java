package com.modeler.services;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Componente;
import com.modeler.repositories.ComponenteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ComponentServices {

    @Autowired
    ComponenteRepository componenteRepository;


    public void addComponent(Componente componente) throws ModelerException {
        for(Componente c: componenteRepository.getComponenteByNombreEquals(componente.getNombre())) {
            if(componente.getModelo().equals(c.getModelo())) throw new ModelerException(ModelerException.claseInvalida);
        }
        componenteRepository.save(componente);
    }

    public List<Componente> getComponents(){
        return componenteRepository.findAll();
    }
    public Componente getComponenteById(int id) throws ModelerException {
        return componenteRepository.findById(id).orElseThrow(()-> new ModelerException(ModelerException.claseInexistente));
    }

}
