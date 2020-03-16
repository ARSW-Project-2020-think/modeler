package com.modeler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modeler.security.JwtRequest;
import com.modeler.security.JwtToken;
import com.modeler.security.JwtUserDetailsService;
import com.modeler.services.UserServices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.modeler.model.Proyecto;
import com.modeler.model.Usuario;
import com.modeler.repositories.ProjectRepository;
import com.modeler.repositories.UserRepository;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect",
        "spring.datasource.url=jdbc:h2:mem:test",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa"
})
@AutoConfigureMockMvc
public class AppTest {
    @Autowired
    private UserRepository repo;
    @Autowired
    private ProjectRepository projects;
    @Autowired
    private MockMvc mock;
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
	private UserServices userService;
	@Autowired
	private JwtToken jwToken;
	@Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Test
    public void testApp() {

    }

    @Test
    public void wouldBeRegisterAnUser() {
    	
    	System.out.println("PRUEBA 1 \n \n");
        try {

            String v = mapper.writeValueAsString(new Usuario("cesarvilla@hotmail.com", "cesar", "123"));
            mock.perform(post("/user").content(v).contentType("application/json")).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void wouldNotBeRegisterAnUserXUserName() {
    	System.out.println("PRUEBA 2 \n \n");
        try {
            repo.save(new Usuario("jeissonsa@hotmail.com", "JaySa", "jay123"));
            String v = mapper.writeValueAsString(new Usuario("cesarvillac@hotmail.com", "JaySa", "123"));
            mock.perform(post("/user").content(v).contentType("application/json")).andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wouldNotBeRegisterAnUserXEmail() {
    	System.out.println("PRUEBA 3 \n \n");
        try {
            repo.save(new Usuario("js@hotmail.com", "JaySan", "jay123"));
            String v = mapper.writeValueAsString(new Usuario("js@hotmail.com", "JaySa1", "123"));
            mock.perform(post("/user").content(v).contentType("application/json")).andExpect(status().isBadRequest());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void wouldBeConsultAnUser() {
    	System.out.println("PRUEBA 4 \n \n");
        try {
        	String pw = new BCryptPasswordEncoder().encode("jay123");
        	//System.out.println("ini "+pw);
            repo.save(new Usuario("cv@hotmail.com", "CeVi", pw));
            String v = mapper.writeValueAsString(new JwtRequest("cv@hotmail.com", "jay123"));
            mock.perform(post("/user/login").content(v).contentType("application/json")).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void wouldBeRegisterAnProyect() throws Exception {
    	System.out.println("PRUEBA 5 \n \n");
    	Usuario u = new Usuario("jay222@mail.com","jaytestapp",new BCryptPasswordEncoder().encode("jay123")); 
    	repo.save(u);
    	Proyecto p = new Proyecto("appcontroller",true);
    	String json = mapper.writeValueAsString(p);
    	mock.perform(post("/projectapi/jaytestapp/project").content(json).contentType("application/json").header("Authorization", getToken("jay222@mail.com"))).andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void shouldNotBeRegisterAnProyectWithTheSameName() throws Exception {
    	System.out.println("PRUEBA 6 \n \n");
    	Usuario u = new Usuario("test@mail.com","nombre",new BCryptPasswordEncoder().encode("test")); 
    	repo.save(u);
    	Proyecto p = new Proyecto("nombre",true);
    	String json = mapper.writeValueAsString(p);
    	mock.perform(post("/projectapi/nombre/project").content(json).contentType("application/json").header("Authorization", getToken("test@mail.com"))).andExpect(status().is2xxSuccessful());
    	
    	p = new Proyecto("nombre",true);
    	json = mapper.writeValueAsString(p);
    	mock.perform(post("/projectapi/nombre/project").content(json).contentType("application/json").header("Authorization", getToken("test@mail.com"))).andExpect(status().is4xxClientError());
    
    }
    
    
    private String getToken(String email) {
    	
    	final UserDetails userDetails = jwtUserDetailsService

                .loadUserByUsername(email);
    	return jwToken.generateToken(userDetails);
    }
}
