package com.modeler.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Usuario;
import com.modeler.repositories.UserRepository;
@Service
public class UserServices {
	@Autowired
	private UserRepository repo;

	public void add(Usuario user) throws ModelerException {
		String pattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@"+"[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";;
		Pattern pat = Pattern.compile(pattern);
		boolean bol=!pat.matcher(user.getCorreo()).matches();
		if(getUsuario(user.getCorreo())!=null) { 
			throw new ModelerException(ModelerException.existeUsuario);
		}
		else if(bol) { 
			System.out.print(user.getCorreo());
			throw new ModelerException(ModelerException.noEsUnCorreoValido);
		}else if(repo.getUsuarioByUsername(user.getUsername())!=null) {
			throw new ModelerException(ModelerException.nombreDeUsuarioInvalido);
		}
		repo.save(user);
	}
	
	public Usuario getUsuario(String email) {
		return repo.findById(email).orElse(null);
	}

	public List<Usuario> getAll() {
		return repo.findAll();
	}

	public Usuario getUsuarioByUsername(String username) {
		return repo.getUsuarioByUsername(username);
	}

	public void update(Usuario u) {
		repo.save(u);
	}
}
