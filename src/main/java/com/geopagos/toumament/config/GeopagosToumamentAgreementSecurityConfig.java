package com.geopagos.toumament.config;

import com.geopagos.toumament.component.JwtAuthenticationEntryPoint;
import com.geopagos.toumament.security.JwtProvider;
import com.geopagos.toumament.security.JwtTokenFilterConfigurer;
import com.geopagos.toumament.security.GeopagosToumamentAccessDeniedHandler;
import com.geopagos.toumament.security.GeopagosToumamentAgreementAuthenticationEntryPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GeopagosToumamentAgreementSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Autowired
    JwtProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/v1/toumament-players/result").permitAll()
                .anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint());

        httpSecurity.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new GeopagosToumamentAgreementAuthenticationEntryPoint();
    }
    @Bean
    AccessDeniedHandler accessDeniedHandler(){
        return new GeopagosToumamentAccessDeniedHandler();
    }

}
