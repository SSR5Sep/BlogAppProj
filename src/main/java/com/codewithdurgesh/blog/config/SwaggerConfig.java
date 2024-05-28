package com.codewithdurgesh.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class SwaggerConfig {
	

	
	
//(DEPRICATED)-All swagger config is done through Docket class
//All swagger config is done through openapi
	
	@Bean
	public OpenAPI openapi()
	{
		String schemeName = "bearerScheme";
				
		return new OpenAPI()
		.addSecurityItem(new SecurityRequirement()
		 .addList(schemeName))
		  .components(new Components()
		   .addSecuritySchemes(schemeName, new SecurityScheme()
		    .name(schemeName)
		     .type(SecurityScheme.Type.HTTP)
		      .bearerFormat("JWT")
			   .scheme("bearer")
						)
				)
			.info(new Info()
		     .title("Blogging Application")
			  .description("This is Blogging Application project API developed by Shivam Singh")
			   .version("1.0")
				.contact(new Contact().name("Shivam").email("shivam.ss5998@gmail.com")
			     .url("shivam.com"))
			      .license(new License().name("Apache")))
				   .externalDocs(new ExternalDocumentation()
				    .url("codewithShivam.com")
				     .description("this is external url"));
			}
		
		
	}

