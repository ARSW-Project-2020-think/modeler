package com.modeler.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Atributo;
import com.modeler.model.Componente;
import com.modeler.model.Metodo;
import com.modeler.model.Modelo;
import com.modeler.model.Ovalo;
import com.modeler.model.Rectangulo;
import com.modeler.repositories.MethodRepository;
import com.modeler.services.AtributeServices;
import com.modeler.services.ComponentServices;
import com.modeler.services.LineServices;
import com.modeler.services.MethodServices;
import com.modeler.services.ModelServices;
import com.modeler.services.RectangleServices;

@Controller
@CrossOrigin(origins="*")
public class WebSocketController {
	@Autowired
	private ModelServices services;
	@Autowired
	private RectangleServices rectangles;
	@Autowired
	private LineServices lines;
	@Autowired
	private MethodServices metodos;
	@Autowired
	private ComponentServices components;
	@Autowired
	private AtributeServices atributes;
	@Autowired
	private SimpMessagingTemplate ms;
	
	@MessageMapping("/newcomponent.{idmodelo}")
	public void add(Componente componente,@DestinationVariable int idmodelo) {
			try {
				Modelo m = services.getModelById(idmodelo);
				componente.setModelo(m);
				Componente cm = (Componente) componente.clone(); 
				components.addComponent(cm);
				ms.convertAndSend("/shape/newcomponent."+idmodelo,services.getModelById(idmodelo).getComponente(componente));
				System.out.println("-----------  -------- Salioo ");
			} catch (ModelerException e) {
				System.out.println(">>>>>>>>>>>>>>><< Hubo un error "+e.getMessage());
				e.printStackTrace();
			}
			System.out.println("entro");
	}
	@MessageMapping("/updatecomponent.{idmodelo}")
	public void update(Componente component,@DestinationVariable int idmodelo) {
		try {
			Componente r = components.getComponenteById(component.getId());
			r.setX(component.getX());
			r.setY(component.getY());
			components.update(r);
			ms.convertAndSend("/shape/updatecomponent."+idmodelo,r);
		} catch (ModelerException e) {
			
		}
	}
	@MessageMapping("/newrelation.{idmodelo}")
	public void addLine(List<Componente> relacion,@DestinationVariable int idmodelo) {
			try {
				Componente r = components.getComponenteById(relacion.get(0).getId());
				Componente r2 = components.getComponenteById(relacion.get(1).getId());
				r.addComponente(r2);
				components.update(r);
				r2.addComponente(r);
				System.out.println("Creo relacion");				
				System.out.println("BORRO Y LLEGA ACA");
				ms.convertAndSend("/shape/newrelation."+idmodelo,new Componente[] {r,r2});
			} catch (ModelerException e) {
				System.out.println("Hubo un error "+e.getMessage());
			}
		
	}
	
	@MessageMapping("/deleteRelation.{idmodelo}")
	public void removeRelation(List<Componente> relacion,@DestinationVariable int idmodelo) {
		try {
			Componente r1 = components.getComponenteById(relacion.get(0).getId());
			Componente r2 = components.getComponenteById(relacion.get(1).getId());
			r1.removerComponenteRelacion(r2);
			r2.removerComponenteRelacion(r1);
			components.update(r1);
			components.update(r2);
			r1 = components.getComponenteById(relacion.get(0).getId());
			r2 = components.getComponenteById(relacion.get(1).getId());
			ms.convertAndSend("/shape/deleteRelation."+idmodelo,new Componente[] {r1,r2});
		}catch(ModelerException e) {
			
		}
	}
	@MessageMapping("/deleteComponent.{idmodelo}")
	public void deleteComponent(Componente componente,@DestinationVariable int idmodelo) {
		try {
			Componente r1 = components.getComponenteById(componente.getId());
			Modelo m = services.getModelById(idmodelo);
			for (Componente c: m.getComponentes()) {
				c.removerComponenteRelacion(r1);
				components.update(c);
			}
			r1.getComponentesRelacionados().clear();
			r1.getRelaciones().clear();
			components.update(r1);
			components.delete(componente);			
			ms.convertAndSend("/shape/deleteComponent."+idmodelo,componente);
		} catch (ModelerException e) {
			System.out.println(">>>>>>>>>>>>> error >>>>>>>>>>>><<<< "+e.getMessage()+"\n\n\n");
		}
	}
	@MessageMapping("/newAtribute.{idmodelo}")
	public void addAtribute(Rectangulo rectangulo,@DestinationVariable int idmodelo) {
		try {
			Rectangulo r = rectangles.getRectangleById(rectangulo.getId());
			Atributo m = rectangulo.getAtributos().get(rectangulo.getAtributos().size()-1);
			Atributo m2 = new Atributo(m.getAtributo(),r);
			atributes.save(m2, r);
			r = rectangles.getRectangleById(rectangulo.getId());
			ms.convertAndSend("/shape/newAtribute."+idmodelo,r);
		} catch (ModelerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@MessageMapping("/deleteAtribute.{idmodelo}")
	public void deleteAtribute(Atributo atributo,@DestinationVariable int idmodelo) {
		try {
			Atributo a = atributes.getAtributoById(atributo.getId());
			atributes.delete(a);
			Rectangulo r = rectangles.getRectangleById(a.getRectangulo().getId());
			ms.convertAndSend("/shape/deleteAtribute."+idmodelo,r);
		} catch (ModelerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@MessageMapping("/newMethod.{idmodelo}")
	public void addMethod(Rectangulo rectangulo,@DestinationVariable int idmodelo) {
		try {
			Rectangulo r = rectangles.getRectangleById(rectangulo.getId());
			Metodo m = rectangulo.getMetodos().get(rectangulo.getMetodos().size()-1);
			Metodo m2 = new Metodo(m.getMetodo(),r);
			metodos.save(m2,r);
			r = rectangles.getRectangleById(rectangulo.getId());
			ms.convertAndSend("/shape/newMethod."+idmodelo,r);
		} catch (ModelerException e) {
			e.printStackTrace();
		}
	}
	@MessageMapping("/deleteMethod.{idmodelo}")
	public void deleteMethod(Metodo metodo,@DestinationVariable int idmodelo) {
		try {
			Metodo m = metodos.getMetodoById(metodo.getId());
			metodos.delete(m);
			Rectangulo r = rectangles.getRectangleById(m.getRectangulo().getId());
			ms.convertAndSend("/shape/deleteMethod."+idmodelo,r);
		} catch (ModelerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
