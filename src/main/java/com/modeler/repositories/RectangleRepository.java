package com.modeler.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.modeler.model.Rectangulo;

public interface RectangleRepository extends JpaRepository<Rectangulo,Integer>{
	@Query("SELECT r from Rectangulo r WHERE r.nombre = ?1")
	List<Rectangulo> getRectanglesByName(String nombre);

}
