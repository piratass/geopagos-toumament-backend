package com.geopagos.toumament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GeopagosToumamentApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GeopagosToumamentApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("PUT", "DELETE","GET","POST","PATCH")
						.allowedOrigins("http://localhost:4200","http://167.235.148.2","http://sds-evolution.com","http://www.sds-evolution.com")
						.allowedHeaders("*");
			}
		};
	}

}
