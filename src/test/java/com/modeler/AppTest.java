package com.modeler;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.modeler.model.Usuario;
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
	private MockMvc mock;
    @Test
    public void testApp()
    {
    	
    }
    /* Verificacion configuracion de base de datos
    @Test
    public void wouldBeRegisterAnUser() {
    	repo.save(new Usuario("Juan@Diaz.com","juan","juan"));
    	System.out.println(repo.findOne("Juan@Diaz.com").getCorreo());
    	assertTrue(true);
    }
    */
    /* Example use
    @Test
    public void wouldBeFindJay() {
    	try {
			mock.perform(get("/api")).andExpect(content().string("Hola"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/
}
