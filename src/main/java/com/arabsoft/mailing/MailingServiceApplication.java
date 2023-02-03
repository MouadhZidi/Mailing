package com.arabsoft.mailing;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@ComponentScan(basePackages = {
	    "com.arabsoft.mailing"
	})
public class MailingServiceApplication implements CommandLineRunner
{
	
	@Autowired
	RepositoryRestConfiguration repositoryRestConfiguration;
	
	
	public static void main(String[] args) {
		SpringApplication.run(MailingServiceApplication.class, args);
	}
	
	

    @Autowired 
    ObjectMapper ojectMapper;

    

    @Bean

    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption, @Value("${application-version}") String appVersion) {

        return new OpenAPI()

          .info(new Info()

          .title("sample application API")

          .version(appVersion)

          .description(appDesciption)

          .termsOfService("http://swagger.io/terms/")

          .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("-------------------------------------");
		System.out.println("* * * Application run correctly * * *");		
		System.out.println("-------------------------------------");
   }
	
}

