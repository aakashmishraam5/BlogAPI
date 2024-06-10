package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Spring Boot Blog REST APIs",
		description = "Spring boot Blog App REST API By Akash Mishra",
		version = "v1.0"
		),externalDocs = @ExternalDocumentation(description = "Project Repository",
		url ="https://github.com/aakashmishraam5/springboot-blog/tree/master" ))
public class SpringBootBlogRestApiApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogRestApiApplication.class, args);
	}

}
