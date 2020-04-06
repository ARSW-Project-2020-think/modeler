package com.modeler.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.modeler.model.Usuario;
import com.modeler.repositories.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository repo;

	public UserDetails loadUserByUsername(String username) {
		Usuario u =  repo.findById(username).get();
		return new User(u.getCorreo(),u.getPassword(),new ArrayList<>());
	}
	
}
