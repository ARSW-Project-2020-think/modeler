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
import com.modeler.model.Modelo;
import com.modeler.model.Rectangulo;
import com.modeler.services.LineServices;
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
	private SimpMessagingTemplate ms;
	
	@MessageMapping("/newrectangle.{idmodelo}")
	public void add(Rectangulo rectangulo,@DestinationVariable int idmodelo) {
			try {
				System.out.println("nuevo rectangulo recibido "+rectangulo);
				Modelo m = services.getModelById(idmodelo);
				Rectangulo r = new Rectangulo(rectangulo.getNombre(),rectangulo.getX(),rectangulo.getY(),rectangulo.getAncho(),rectangulo.getAlto());
				r.setModelo(m);
				rectangles.save(r);
				ms.convertAndSend("/shape/newrectangle."+idmodelo,services.getModelById(idmodelo).getRectangulo(r.getNombre()));
				System.out.println("-----------  -------- Salioo "+r+"  "+" modelo: "+idmodelo);
			} catch (ModelerException e) {
				System.out.println(">>>>>>>>>>>>>>><< Hubo un error "+e.getMessage());
				e.printStackTrace();
			}
			System.out.println("entro");
	}
	@MessageMapping("/updaterectangle.{idmodelo}")
	public void update(Rectangulo rectangulo,@DestinationVariable int idmodelo) {
		try {
			Rectangulo r = rectangles.getRectangleById(rectangulo.getId());
			r.setX(rectangulo.getX());
			r.setY(rectangulo.getY());
			rectangles.update(r);
			ms.convertAndSend("/shape/updaterectangle."+idmodelo,r);
		} catch (ModelerException e) {
			
		}
	}
	@MessageMapping("/newrelation.{idmodelo}")
	public void addLine(List<Rectangulo> relacion,@DestinationVariable int idmodelo) {
			try {
				Rectangulo r = rectangles.getRectangleById(relacion.get(0).getId());
				Rectangulo r2 = rectangles.getRectangleById(relacion.get(1).getId());
				r.addComponente(r2);
				rectangles.update(r);
				r2.addComponente(r);
				System.out.println("Creo relacion");				
				ms.convertAndSend("/shape/newrelation."+idmodelo,new Rectangulo[] {r,r2});
			} catch (ModelerException e) {
				System.out.println("Hubo un error "+e.getMessage());
			}
		
	}
	@MessageMapping("/deleteRelation.{idmodelo}")
	public void removeRelation(List<Rectangulo> relacion,@DestinationVariable int idmodelo) {
		try {
			Rectangulo r1 = rectangles.getRectangleById(relacion.get(0).getId());
			Rectangulo r2 = rectangles.getRectangleById(relacion.get(1).getId());
			r1.removerComponenteRelacion(r2);
			r2.removerComponenteRelacion(r1);
			rectangles.update(r1);
			rectangles.update(r2);
			r1 = rectangles.getRectangleById(relacion.get(0).getId());
			r2 = rectangles.getRectangleById(relacion.get(1).getId());
			ms.convertAndSend("/shape/deleteRelation."+idmodelo,new Rectangulo[] {r1,r2});
		}catch(ModelerException e) {
			
		}
	}
	
	
}
