package com.modeler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modeler.model.Modelo;

public interface ModelRepository extends JpaRepository<Modelo,Integer>{

}
