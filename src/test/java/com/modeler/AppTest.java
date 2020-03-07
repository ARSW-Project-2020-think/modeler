package com.modeler;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.modeler.controllers.UserController;
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
public class AppTest {
	@Autowired
	private UserController user;
	@Autowired
	private UserRepository repo;
    @Test
    public void testApp()
    {
    }
    @Test
    public void wouldBeRegisterAnUser() {
    	repo.save(new Usuario("Juan@Diaz.com","juan","juan"));
    	System.out.println(repo.findOne("Juan@Diaz.com").getCorreo());
    	assertTrue(true);
    }
}
