package com.modeler.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Componente;
import com.modeler.model.Metodo;
import com.modeler.model.Modelo;
import com.modeler.model.Ovalo;
import com.modeler.model.Rectangulo;
import com.modeler.repositories.MethodRepository;
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
				Componente r2 = rectangles.getRectangleById(relacion.get(1).getId());
				r.addComponente(r2);
				components.update(r);
				r2.addComponente(r);
				System.out.println("Creo relacion");				
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
	public void removeRelation(Componente componente,@DestinationVariable int idmodelo) {
		try {
			components.delete(componente);
			ms.convertAndSend("/shape/deleteRectangle."+idmodelo,componente);
		} catch (ModelerException e) {
			System.out.println(">>>>>>>>>>>>> error >>>>>>>>>>>><<<< "+e.getMessage()+"\n\n\n");
		}
	}
	@MessageMapping("/newMethod.{idmodelo}")
	public void addMethod(Rectangulo rectangulo,@DestinationVariable int idmodelo) {
		try {
			Rectangulo r = rectangles.getRectangleById(rectangulo.getId());
			Metodo m = r.getMetodos().get(r.getMetodos().size()-1);
			Metodo m2 = new Metodo(m.getMetodo(),r);
			r.addMetodo(m2);
			r = rectangles.getRectangleById(rectangulo.getId());
			ms.convertAndSend("/shape/newMethod."+idmodelo,r);
		} catch (ModelerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@MessageMapping("/deleteMethod.{idmodelo}")
	public void deleteMethod(Metodo metodo,@DestinationVariable int idmodelo) {
		try {
			Metodo m = metodos.getModeloById(metodo.getId());
			metodos.delete(m);
			ms.convertAndSend("/shape/deleteMethod."+idmodelo,m);
		} catch (ModelerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@MessageMapping("/newOval.{idmodelo}")
	public void addOval(Ovalo oval,@DestinationVariable int idmodelo) {
		try {
			Modelo m = services.getModelById(idmodelo);
			Ovalo ov = new Ovalo(oval.getX(),oval.getY(),m);
			components.addComponent(ov);
			m = services.getModelById(idmodelo);
			ms.convertAndSend("/shape/newOval."+idmodelo,m.getComponente(ov));
		} catch (ModelerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
