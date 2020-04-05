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
			updateLines(rectangulo,r,services.getModelById(idmodelo));
			r.setX(rectangulo.getX());
			r.setY(rectangulo.getY());
			rectangles.update(r);
			ms.convertAndSend("/shape/updaterectangle."+idmodelo,r);
		} catch (ModelerException e) {
			
		}
	}
	@MessageMapping("/newline.{idmodelo}")
	public void addLine(Linea linea,@DestinationVariable int idmodelo) {
		try {
			Modelo modelo = services.getModelById(idmodelo);
			linea.setModelo(modelo);
			lines.save(linea);
			modelo = services.getModelById(idmodelo);
			ms.convertAndSend("/shape/newline."+idmodelo,modelo.getLinea(linea));
		}catch(ModelerException e) {
			System.out.println(">>>>>>>>>>>> Error >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(">>>>>>>>>>>> Error >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(">>>>>>>>>>>> Error >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(">>>>>>>>>>>> Error >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		}
		
	}
	
	public void updateLines(Rectangulo newrectangle,Rectangulo old,Modelo m) {
		for(Linea l:m.getLineas()) {
			if(old.getX()<l.getX1() && l.getX1()<old.getX()+old.getAncho() && old.getY()<l.getY1() && l.getY1()<old.getY()+old.getAlto()) {
				l.setX1((newrectangle.getX()+(newrectangle.getAncho()/2)));
				l.setY1((newrectangle.getY()+(newrectangle.getAlto()/2)));
				lines.update(l);
				ms.convertAndSend("/shape/updateline."+m.getId(),l);
			}else if(old.getX()<l.getX2() && l.getX2()<old.getX()+old.getAncho() && old.getY()<l.getY2() && l.getY2()<old.getY()+old.getAlto()) {
				l.setX2((newrectangle.getX()+(newrectangle.getAncho()/2)));
				l.setY2((newrectangle.getY()+(newrectangle.getAlto()/2)));
				lines.update(l);
				ms.convertAndSend("/shape/updateline."+m.getId(),l);
			}
		}
	}
	
}
