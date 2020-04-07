package com.modeler.controllers;

import java.awt.Rectangle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Linea;
import com.modeler.model.Modelo;
import com.modeler.model.Rectangulo;
import com.modeler.services.LineServices;
import com.modeler.services.ModelServices;
import com.modeler.services.RectangleServices;

@Controller
@CrossOrigin(origins="*")
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate ms;
	@Autowired
	private ModelServices services;
	@Autowired
	private RectangleServices rectangles;
	@Autowired
	private LineServices lines;
	
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
	public void addLine(Rectangulo relacion,@DestinationVariable int idmodelo) {
			try {
				Rectangulo r = rectangles.getRectangleById(relacion.getId());
				r.setRelaciones(relacion.getRelaciones());
				rectangles.save(r);
				ms.convertAndSend("/shape/updaterectangle."+idmodelo,relacion);
			} catch (ModelerException e) {
				System.out.println("Hubo un error "+e.getMessage());
			}
		
	}
	
	
}
