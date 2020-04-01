package com.modeler.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.modeler.exceptions.ModelerException;
import com.modeler.model.Rectangulo;
import com.modeler.services.RectangleServices;

@Controller
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate ms;
	@Autowired
	private RectangleServices services;
	
	@MessageMapping("/rectangulo/modelo.{id}")
	public void add(Rectangulo rectangulo,@DestinationVariable int id) {
		try {
			System.out.println("nuevo rectangulo recibido");
			services.save(rectangulo);
			ms.convertAndSend("/shape/rectangle/modelo."+id,rectangulo);
			System.out.println("entro");
		} catch (ModelerException e) {
			System.out.println("Error");
		}
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
