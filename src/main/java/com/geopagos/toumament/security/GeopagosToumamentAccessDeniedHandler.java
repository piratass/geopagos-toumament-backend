package com.geopagos.toumament.security;

import com.geopagos.toumament.constants.GeopagosToumamentConstants;
import com.geopagos.toumament.errorhandler.GeopagosToumamentError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Component
public class GeopagosToumamentAccessDeniedHandler implements AccessDeniedHandler  {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        GeopagosToumamentError wallexError = new GeopagosToumamentError(HttpStatus.FORBIDDEN,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR);
        wallexError.setMessage("Forbidden");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        OutputStream outputStream=response.getOutputStream();
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(outputStream, wallexError);
        outputStream.flush();
    }

}
