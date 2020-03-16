package com.modeler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Proyecto;
import com.modeler.services.ProjectServices;

@RestController
@RequestMapping(value="/proyecto")
public class ProjectController {
	
	@Autowired
	private ProjectServices projectServices;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> addProject(@RequestBody Proyecto proyecto){
		try {
			projectServices.add(proyecto);
		} catch (ModelerException e) {
			return new ResponseEntity<>("Error, No project add",HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(projectServices.getAll(),HttpStatus.OK);
	}
}
