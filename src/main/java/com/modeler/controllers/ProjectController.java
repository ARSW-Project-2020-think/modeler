package com.modeler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Proyecto;
import com.modeler.model.Usuario;
import com.modeler.services.ProjectServices;
import com.modeler.services.UserServices;

@RestController
@CrossOrigin
@RequestMapping(value="/projectapi")
public class ProjectController {
	
	@Autowired
	private ProjectServices projectServices;
	@Autowired
	private UserServices userServices;
	
	@RequestMapping(value="/{username}/project",method=RequestMethod.POST)
	public ResponseEntity<?> addProject(@PathVariable String username,@RequestBody Proyecto proyecto){
		Usuario u=null;
		System.out.println("Ini "+proyecto.toString());
		try {
			u = userServices.getUsuarioByUsername(username);
			Proyecto p = new Proyecto();
			p.setNombre(proyecto.getNombre());
			p.setPublico(proyecto.getPublico());
			p.setAutor(u);
			projectServices.add(p);
		} catch (ModelerException e) {
			//System.out.println(">>>>>>>>>> error"+e.getMessage());
			return new ResponseEntity<>("Error, No project add",HttpStatus.BAD_GATEWAY);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){
		return new ResponseEntity<>(projectServices.getAll(),HttpStatus.OK);
	}
}
