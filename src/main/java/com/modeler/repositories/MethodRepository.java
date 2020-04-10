package com.modeler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modeler.model.Metodo;

public interface MethodRepository extends JpaRepository<Metodo,Integer>{

}
