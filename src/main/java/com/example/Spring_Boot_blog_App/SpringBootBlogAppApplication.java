package com.example.Spring_Boot_blog_App;

import com.example.Spring_Boot_blog_App.entities.Role;
import com.example.Spring_Boot_blog_App.repositories.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@OpenAPIDefinition(info = @Info(title = "Spring Boot Blog App REST APIs",
							   description = "Spring Boot Blog App REST APIs Documentation",
							   version = "v1.0",
							   contact = @Contact(name = "Ilyas",
												  email = "ilyass.jaddaoui1@gmail.com")),
		           externalDocs = @ExternalDocumentation(description = "Spring Boot Blog App Documentation",
				                                         url = "") // github

)

public class SpringBootBlogAppApplication  {

	@Bean // configuration of model mapper
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogAppApplication.class, args);
	}


}
