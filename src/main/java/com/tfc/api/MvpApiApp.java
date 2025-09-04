package com.tfc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.common.base.Predicates;
import com.tfc.controller.BusinessController;
import com.tfc.exception.TfcExceptionHandler;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;


@SpringBootApplication(scanBasePackages={
		"com.tfc.test","com.tfc.controller","com.tfc.dto","com.tfc.model"})
@ComponentScan({"com.tfc.dto"})
@ComponentScan(basePackageClasses = BusinessController.class)
@EntityScan("com.tfc.model")
@EnableSwagger2
@EnableJpaRepositories({"com.tfc.repository"})
@Import(TfcExceptionHandler.class)
@EnableAutoConfiguration
public class MvpApiApp {

	public static void main(String args[]) {
		SpringApplication.run(MvpApiApp.class, args);
	}
	
	 @Bean
	  public Docket docket() {
	    return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false)
	    		.apiInfo(apiInfo()).select()
	            .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
	            //.useDefaultResponseMessages(false)
	            .build();
	  }

	  private ApiInfo apiInfo() {
	    return new ApiInfoBuilder().title("Together for Climate MVP API")
	            .description("This API can be used to get business, donations, projects and ngo related actions and information for TFC")
	            .version("V1.0").build();
	  }
}
