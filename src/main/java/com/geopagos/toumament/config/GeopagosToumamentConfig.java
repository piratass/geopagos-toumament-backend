package com.geopagos.toumament.config;


import com.geopagos.toumament.errorhandler.rest.RestErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class GeopagosToumamentConfig {
    final
    Environment env;

    public GeopagosToumamentConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.errorHandler(new RestErrorHandler()).build();
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
    }

}
