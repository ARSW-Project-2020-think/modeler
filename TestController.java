package com.modeler.controller
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestContoler
@RequestMapping(value="/test")
public class TestController {
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> test(){
		return new ResponseEntity<>("Hola mundo",HttpStatus.ACCEPTED);
	}
}
