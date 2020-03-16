package com.modeler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modeler.model.Proyecto;

public interface ProjectRepository extends JpaRepository<Proyecto, Integer>{
	
}
