package com.geopagos.toumament.security;

import com.geopagos.toumament.constants.GeopagosToumamentConstants;
import com.geopagos.toumament.errorhandler.GeopagosToumamentError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class GeopagosToumamentAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        GeopagosToumamentError wallexError = new GeopagosToumamentError(HttpStatus.UNAUTHORIZED, GeopagosToumamentConstants.PREFIX_CLIENT_ERROR+ GeopagosToumamentConstants.UNAUTHORIZED);
        wallexError.setMessage("Unauthorized");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream outputStream=response.getOutputStream();
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(outputStream, wallexError);
        outputStream.flush();
    }
}
