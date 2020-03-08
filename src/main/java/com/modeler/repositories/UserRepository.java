package com.modeler.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.modeler.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario,String>{
	@Query("SELECT u FROM Usuario u WHERE u.username=?1")
	public Usuario getUsuarioByUsername(String username);
}
