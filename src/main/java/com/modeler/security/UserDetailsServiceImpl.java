package com.modeler.security;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.modeler.model.Usuario;
import com.modeler.repositories.UserRepository;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository applicationUserRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario applicationUser = applicationUserRepository.findOne(correo);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(correo);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}