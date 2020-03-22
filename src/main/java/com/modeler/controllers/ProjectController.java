package com.modeler.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Proyecto;
import com.modeler.model.Usuario;
import com.modeler.model.Version;
import com.modeler.services.ProjectServices;
import com.modeler.services.UserServices;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value="/projectapi")
public class ProjectController {
	
	@Autowired
	private ProjectServices projectServices;
	@Autowired
	private UserServices userServices;
	
	@RequestMapping(value="/{username}/project",method=RequestMethod.POST)
	public ResponseEntity<?> addProject(@PathVariable String username,@RequestBody Proyecto proyecto, Authentication auth){
		if (!auth.getName().equals(userServices.getUsuarioByUsername(username).getCorreo())) {
			return new ResponseEntity<>("Error, FORBIDDEN add project",HttpStatus.FORBIDDEN);
		}
		Usuario u=null;
		try {
			u = userServices.getUsuarioByUsername(username);
			Proyecto p = new Proyecto();
			p.setNombre(proyecto.getNombre());
			p.setPublico(proyecto.getPublico());
			p.setAutor(u);
			ArrayList<Version> versiones = new ArrayList<>();
			versiones.add(new Version(1));
			p.setVersiones(versiones);
			projectServices.add(p);
		} catch (ModelerException e) {
			//System.out.println(">>>>>>>>>> error"+e.getMessage());
			return new ResponseEntity<>("Error, No project add",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> getAll(){		
		return new ResponseEntity<>(projectServices.getAll(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{username}/project",method=RequestMethod.GET)
	public ResponseEntity<?> getAllProjectsByUser(@PathVariable String username, Authentication auth){
		Usuario u = userServices.getUsuarioByUsername(username);
		if (!u.getCorreo().equals(auth.getName())) {
			System.out.println("entro 1");
			System.out.println(projectServices.getPublicProjectsByusuario(username));
			return new ResponseEntity<>(projectServices.getPublicProjectsByusuario(username),HttpStatus.OK);
		}
		System.out.println("entro 2");
		System.out.println(u.getProyectos());
		return new ResponseEntity<>(u.getProyectos(),HttpStatus.OK);
	}
}
