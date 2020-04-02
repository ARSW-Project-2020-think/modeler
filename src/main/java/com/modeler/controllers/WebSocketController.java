package com.modeler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Modelo;
import com.modeler.model.Rectangulo;
import com.modeler.services.ModelServices;
import com.modeler.services.RectangleServices;

@Controller
@CrossOrigin(origins="*")
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate ms;
	@Autowired
	private ModelServices services;
	
	@MessageMapping("/newrectangle.{idmodelo}")
	public void add(Rectangulo rectangulo,@DestinationVariable int idmodelo) {
			try {
				System.out.println("nuevo rectangulo recibido "+rectangulo);
				Modelo m = services.getModelById(idmodelo);
				Rectangulo r = new Rectangulo(rectangulo.getNombre(),rectangulo.getX(),rectangulo.getY(),rectangulo.getAncho(),rectangulo.getAlto());
				m.addRectangulo(r);
				services.save(m);
				ms.convertAndSend("/shape/newrectangle."+idmodelo,r);
				System.out.println("-----------  -------- Salioo "+r+"  "+" modelo: "+idmodelo);
			} catch (ModelerException e) {
				System.out.println(">>>>>>>>>>>>>>><< Hubo un error "+e.getMessage());
				e.printStackTrace();
			}
			System.out.println("entro");
	}
	/**
	@MessageMapping("/rectangulo/modelo.{id}/update")
	public void updateRectangle(Rectangulo rect,@DestinationVariable String id) {
		try {
			services.update(rect);
			ms.convertAndSend("/shape/rectangle/modelo.{id}/update",rect);
		} catch (ModelerException e) {
			
		}
	}*/
	
}
