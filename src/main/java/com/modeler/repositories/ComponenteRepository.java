package com.modeler.repositories;

import com.modeler.model.Componente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponenteRepository extends JpaRepository<Componente,Integer> {
}
