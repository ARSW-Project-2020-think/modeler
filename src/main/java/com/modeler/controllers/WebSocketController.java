package com.modeler.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Modelo;
import com.modeler.model.Rectangulo;
import com.modeler.services.ModelServices;
import com.modeler.services.RectangleServices;

@Controller
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate ms;
	@Autowired
	private ModelServices services;
	
	@MessageMapping("/rectangulo/modelo.{id}")
	public void add(Rectangulo rectangulo,@DestinationVariable String id) {
			try {
				System.out.println("nuevo rectangulo recibido");
				Modelo m = services.getModelById(Integer.parseInt(id));
				Rectangulo r = new Rectangulo(rectangulo.getNombre(),rectangulo.getX(),rectangulo.getY(),rectangulo.getAncho(),rectangulo.getAlto());
				m.addRectangulo(r);
				services.save(m);
				ms.convertAndSend("/shape/rectangulo/modelo."+id,rectangulo);
			} catch (ModelerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ms.convertAndSend("/shape/rectangulo/modelo."+id,rectangulo);
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
