package com.modeler.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class ModelerController {
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> index() {
		return new ResponseEntity<>("Hola mundo",HttpStatus.ACCEPTED);
	}
}
