package com.modeler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modeler.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario,String>{

}
