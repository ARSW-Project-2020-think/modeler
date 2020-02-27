package com.modeler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modeler.repositories.UserRepository;
import com.modeler.services.UserServices;

@RestController
@RequestMapping(value="/")
public class ModelerController {
	@Autowired
	private UserServices userService;
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> index() {
		return new ResponseEntity<>("Hola mundo",HttpStatus.ACCEPTED);
	}
}
