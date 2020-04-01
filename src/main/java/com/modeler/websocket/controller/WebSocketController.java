package com.modeler.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.modeler.services.RectangleServices;

@Controller
public class WebSocketController {
	@Autowired
	private SimpMessagingTemplate msgt;
	@Autowired
	private RectangleServices services;
	
	@MessageMapping("/test")
	public void test() {
		
	}
}
