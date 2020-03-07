package com.modeler.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Usuario;
import com.modeler.repositories.UserRepository;
@Service
public class UserServices {
	@Autowired
	private UserRepository repo;

	public void add(Usuario user) throws ModelerException{
		if(getUsuario(user.getCorreo())!=null) throw new ModelerException(ModelerException.existeUsuario);
		repo.save(user);
	}
	
	public Usuario getUsuario(String email) {
		return repo.findOne(email);
	}

	public List<Usuario> getAll() {
		return repo.findAll();
	}
}
