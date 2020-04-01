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
@CrossOrigin(origins="*")
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate ms;
	@Autowired
	private RectangleServices services;
	
	@MessageMapping("/rectangulo/modelo.{id}")
	public void add(Rectangulo rectangulo,@DestinationVariable int id) {
		try {
			services.save(rectangulo);
			ms.convertAndSend("/shape/rectangle/modelo."+id,rectangulo);
		} catch (ModelerException e) {
			
		}
	}
	@MessageMapping("/rectangulo/modelo.{id}/update")
	public void updateRectangle(Rectangulo rect,@DestinationVariable String id) {
		try {
			services.update(rect);
			ms.convertAndSend("/shape/rectangle/modelo.{id}/update",rect);
		} catch (ModelerException e) {
			
		}
	}
	
}
