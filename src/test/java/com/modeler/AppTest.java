package com.modeler;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.modeler.model.Modelo;
import com.modeler.model.Proyecto;
import com.modeler.model.Rectangulo;
import com.modeler.model.Usuario;
import com.modeler.model.Version;
import com.modeler.repositories.ModelRepository;
import com.modeler.repositories.ProjectRepository;
import com.modeler.repositories.UserRepository;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
		/*"jdbc.driverClassName=org.h2.Driver",
		"jdbc.url=jdbc:h2:mem:db;db_CLOSE_DELAY=-1",
		"jdbc.username=sa",
		"jdbc.password=sa",*/
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
    private ModelRepository models;
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

            String v = mapper.writeValueAsString(new Usuario("cesarvilla@hotmail.com", "cesar", "Contraseña123+"));
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
    
    
    @Test
    public void shouldNotBeRegisterAnProyectOfDiferentUser() throws Exception {
    	System.out.println("PRUEBA 7 \n \n");
    	Usuario u = new Usuario("test1@mail.com","nombre1",new BCryptPasswordEncoder().encode("test1")); 
    	repo.save(u);
    	Usuario u2 = new Usuario("test2@mail.com","nombre2",new BCryptPasswordEncoder().encode("test1")); 
    	repo.save(u2);
    	Proyecto p = new Proyecto("nombre1",true);
    	String json = mapper.writeValueAsString(p);
    	mock.perform(post("/projectapi/nombre1/project").content(json).contentType("application/json").header("Authorization", getToken("test2@mail.com"))).andExpect(status().isForbidden());
    
    }
    @Test
    public void shouldBeShowAPublicProjects() throws JsonProcessingException, Exception {
    	System.out.println("PRUEBA 8 \n \n");
    	Usuario u1 = new Usuario("test3@mail.com","people1",new BCryptPasswordEncoder().encode("test1"));
    	Usuario u2 = new Usuario("test4@mail.com","people2",new BCryptPasswordEncoder().encode("test1")); 
    	repo.save(u1);
    	repo.save(u2);
    	Proyecto p = new Proyecto();
    	p.setAutor(u1);
    	p.setNombre("App");
    	p.setPublico(true);
    	projects.save(p);
    	mock.perform(get("/projectapi/people1/project").header("Authorization", getToken("test4@mail.com"))).andExpect(content().json(mapper.writeValueAsString(projects.getPublicProjectsByusuario("people1"))));	
    }
    
    @Test
    public void notSouldBeShowAPrivateProjects() throws JsonProcessingException, Exception {
    	System.out.println("PRUEBA 9 \n \n");
    	Usuario u1 = new Usuario("test5@mail.com","people3",new BCryptPasswordEncoder().encode("test1"));
    	Usuario u2 = new Usuario("test6@mail.com","people4",new BCryptPasswordEncoder().encode("test1")); 
    	repo.save(u1);
    	repo.save(u2);
    	Proyecto p = new Proyecto();
    	p.setAutor(u1);
    	p.setNombre("App");
    	p.setPublico(false);
    	projects.save(p);
    	mock.perform(get("/projectapi/people4/project").header("Authorization", getToken("test5@mail.com"))).andExpect(content().json("[]"));	
    }
    public void shouldBeShowAllMyProjects() throws JsonProcessingException, Exception {
    	System.out.println("PRUEBA 10 \n \n");
    	Usuario u2 = new Usuario("test7@mail.com","people5",new BCryptPasswordEncoder().encode("test1")); 
    	repo.save(u2);
    	Proyecto p = new Proyecto();
    	p.setAutor(u2);
    	p.setNombre("App");
    	p.setPublico(false);
    	projects.save(p);
    	p = new Proyecto();
    	p.setAutor(u2);
    	p.setNombre("App2");
    	p.setPublico(true);
    	projects.save(p);
    	mock.perform(get("/projectapi/people4/project").header("Authorization", getToken("test5@mail.com"))).andExpect(content().json(mapper.writeValueAsString(projects.getProjectsByusuario("people5"))));	
    }
    
    private String getToken(String email) {
    	
    	final UserDetails userDetails = jwtUserDetailsService

                .loadUserByUsername(email);
    	return jwToken.generateToken(userDetails);
    }
            
    
   @Test
    public void shouldBeRegisterAnModel() {
    	System.out.println("PRUEBA 11 \n \n");
    	try { 
    		Usuario u = new Usuario("modeloPrueba@mail.com","pruebaModelo",new BCryptPasswordEncoder().encode("test1")); 
        	repo.save(u);
        	Proyecto p = new Proyecto("pruebaModel",true,u);
        	String json = mapper.writeValueAsString(p);
        	mock.perform(post("/projectapi/pruebaModelo/project").content(json).contentType("application/json").header("Authorization", getToken("modeloPrueba@mail.com"))).andExpect(status().is2xxSuccessful());
        	Modelo m = new Modelo("pruebaModel",null);
        	json = mapper.writeValueAsString(m);
        	mock.perform(post("/projectapi/pruebaModelo/project/pruebaModel/version/1/modelo").content(json).contentType("application/json").header("Authorization", getToken("modeloPrueba@mail.com"))).andExpect(status().is2xxSuccessful());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	
    }
    
    @Test
    public void souldntBeRegisterAnModelWithRepeatName() {
    	System.out.println("PRUEBA 12 \n \n");
    	try {
    		Usuario u = new Usuario("test10@mail.com","people20",new BCryptPasswordEncoder().encode("test1")); 
        	repo.save(u);
        	Proyecto p = new Proyecto("me",true,u);
        	String json = mapper.writeValueAsString(p);
        	mock.perform(post("/projectapi/people20/project").content(json).contentType("application/json").header("Authorization", getToken("test10@mail.com"))).andExpect(status().is2xxSuccessful());
        	Modelo m = new Modelo("Calc",null);
        	json = mapper.writeValueAsString(m);
        	mock.perform(post("/projectapi/people20/project/me/version/1/modelo").content(json).contentType("application/json").header("Authorization", getToken("test10@mail.com"))).andExpect(status().is2xxSuccessful());
        	mock.perform(post("/projectapi/people20/project/me/version/1/modelo").content(json).contentType("application/json").header("Authorization", getToken("test10@mail.com"))).andExpect(status().is4xxClientError());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	
    }
    
    
    @Test
    public void shouldBeRegisterRectangle() {
    	System.out.println("PRUEBA 13 \n \n");
    	try {
	    	Usuario u = new Usuario("pruebaRectangulo@mail.com","UsuarioR",new BCryptPasswordEncoder().encode("test1"));  
        	repo.save(u);
        	Proyecto p = new Proyecto("proyectoR",true,u);
        	String json = mapper.writeValueAsString(p);
        	mock.perform(post("/projectapi/UsuarioR/project").content(json).contentType("application/json").header("Authorization", getToken("pruebaRectangulo@mail.com"))).andExpect(status().is2xxSuccessful());
        	Modelo m = new Modelo("ModelR",null);
        	json = mapper.writeValueAsString(m);
        	mock.perform(post("/projectapi/UsuarioR/project/proyectoR/version/1/modelo").content(json).contentType("application/json").header("Authorization", getToken("pruebaRectangulo@mail.com"))).andExpect(status().is2xxSuccessful());        	
	    	Rectangulo rectangulo = new Rectangulo("Class", 15, 15, 200, 50, m);
	    	json = mapper.writeValueAsString(rectangulo);
	    	mock.perform(post("/projectapi/UsuarioR/project/proyectoR/version/1/modelo/ModelR/rectangle").content(json).contentType("application/json").header("Authorization", getToken("pruebaRectangulo@mail.com"))).andExpect(status().is2xxSuccessful());
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	
    }
    
    @Test
    public void shouldNotBeRegisterRectangleWithTheSameName() {
    	System.out.println("PRUEBA 14 \n \n");
    	try {
	    	Usuario u = new Usuario("pruebaRectangulo2@mail.com","UsuarioRN",new BCryptPasswordEncoder().encode("test1"));  
        	repo.save(u);
        	Proyecto p = new Proyecto("proyectoRN",true,u);
        	String json = mapper.writeValueAsString(p);
        	mock.perform(post("/projectapi/UsuarioRN/project").content(json).contentType("application/json").header("Authorization", getToken("pruebaRectangulo2@mail.com"))).andExpect(status().is2xxSuccessful());
        	Modelo m = new Modelo("ModelRN",null);
        	json = mapper.writeValueAsString(m);
        	mock.perform(post("/projectapi/UsuarioRN/project/proyectoRN/version/1/modelo").content(json).contentType("application/json").header("Authorization", getToken("pruebaRectangulo2@mail.com"))).andExpect(status().is2xxSuccessful());        	
	    	Rectangulo rectangulo = new Rectangulo("Class", 15, 15, 200, 50, m);
	    	json = mapper.writeValueAsString(rectangulo);
	    	mock.perform(post("/projectapi/UsuarioRN/project/proyectoRN/version/1/modelo/ModelRN/rectangle").content(json).contentType("application/json").header("Authorization", getToken("pruebaRectangulo2@mail.com"))).andExpect(status().is2xxSuccessful());
	    	mock.perform(post("/projectapi/UsuarioRN/project/proyectoRN/version/1/modelo/ModelRN/rectangle").content(json).contentType("application/json").header("Authorization", getToken("pruebaRectangulo2@mail.com"))).andExpect(status().is4xxClientError());
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    }
    @Test
    public void shouldBeAddAnProyectToSahre() throws  Exception {
    	Usuario u = new Usuario("pruebaShare1@mail.com","share1",new BCryptPasswordEncoder().encode("test1"));  
    	repo.save(u);
    	u = new Usuario("pruebaShare2@mail.com","share2",new BCryptPasswordEncoder().encode("test1"));  
    	repo.save(u);
    	Proyecto p = new Proyecto("axxx",false);
    	mock.perform(post("/projectapi/share1/project").content(mapper.writeValueAsString(p)).contentType("application/json").header("Authorization", getToken("pruebaShare1@mail.com"))).andExpect(status().is2xxSuccessful());
    	mock.perform(put("/projectapi/share1/share/share2/project/axxx").header("Authorization", getToken("pruebaShare1@mail.com"))).andExpect(status().is2xxSuccessful());
    }
    @Test
    public void shouldntBeAddAnProjetToShareIfExist() throws Exception{
    	Usuario u = new Usuario("pruebaShare3@mail.com","share3",new BCryptPasswordEncoder().encode("test1"));  
    	repo.save(u);
    	u = new Usuario("pruebaShare4@mail.com","share4",new BCryptPasswordEncoder().encode("test1"));  
    	repo.save(u);
    	Proyecto p = new Proyecto("axxx",false);
    	mock.perform(post("/projectapi/share3/project").content(mapper.writeValueAsString(p)).contentType("application/json").header("Authorization", getToken("pruebaShare3@mail.com"))).andExpect(status().is2xxSuccessful());
    	mock.perform(put("/projectapi/share3/share/share4/project/axxx").header("Authorization", getToken("pruebaShare3@mail.com"))).andExpect(status().is2xxSuccessful());
    	mock.perform(put("/projectapi/share3/share/share4/project/axxx").header("Authorization", getToken("pruebaShare3@mail.com"))).andExpect(status().is4xxClientError());
    }
}
