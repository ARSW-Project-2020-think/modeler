package com.modeler.repositories;

import com.modeler.model.Componente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComponenteRepository extends JpaRepository<Componente,Integer> {

    List<Componente> getComponenteByNombreEquals(String nombre);

}
