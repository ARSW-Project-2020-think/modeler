package com.modeler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Usuario;
import com.modeler.security.JwtRequest;
import com.modeler.security.JwtResponse;
import com.modeler.security.JwtToken;
import com.modeler.security.JwtUserDetailsService;
import com.modeler.services.UserServices;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserServices userService;
	@Autowired
	private JwtToken jwToken;
	@Autowired
    private JwtUserDetailsService jwtUserDetailsService;
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Usuario user){
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			userService.add(user);
			return new ResponseEntity<>(user,HttpStatus.OK);
		} catch (ModelerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest){
		final UserDetails userDetails = jwtUserDetailsService

                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwToken.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
	}
	
}
