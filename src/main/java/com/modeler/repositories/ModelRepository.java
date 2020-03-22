package com.modeler.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.modeler.model.Modelo;

public interface ModelRepository extends JpaRepository<Modelo,Integer>{
	@Query("SELECT m FROM Modelo m WHERE m.nombre = ?1")
	List<Modelo> getModelosByName(String nombre);

}
