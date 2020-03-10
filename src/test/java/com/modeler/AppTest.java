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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testApp() {

    }

    @Test
    public void wouldBeRegisterAnUser() {
        try {

            String v = mapper.writeValueAsString(new Usuario("cesarvilla@hotmail.com", "cesar", "123"));
            mock.perform(post("/user").content(v).contentType("application/json")).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void wouldNotBeRegisterAnUserXUserName() {

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
        try {
            repo.save(new Usuario("cv@hotmail.com", "CeVi", "jay123"));
            String v = mapper.writeValueAsString(new JwtRequest("cv@hotmail.com", "jay123"));
            mock.perform(get("/user/login").content(v).contentType("application/json")).andExpect(status().is2xxSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
