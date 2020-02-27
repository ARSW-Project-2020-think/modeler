package com.modeler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.modeler.repositories")
@ComponentScan(basePackages = {"com.modeler"})

public class ModelerApp
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ModelerApp.class,args);
    }
}
