package com.modeler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.modeler"})

public class ModelerApp
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ModelerApp.class,args);
    }
}
