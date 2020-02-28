package com.modeler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Usuario;
import com.modeler.repositories.UserRepository;
import com.modeler.services.UserServices;

@RestController
@CrossOrigin
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	private UserServices userService;
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> get(){
		return new ResponseEntity<>("Hola mundo",HttpStatus.ACCEPTED);
	}
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Usuario user){
		System.out.println("+++++++++++"+user.getPassword());
		//userService.add(user);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
}
