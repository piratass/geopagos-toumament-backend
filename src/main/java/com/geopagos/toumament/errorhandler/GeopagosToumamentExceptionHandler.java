package com.geopagos.toumament.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.geopagos.toumament.constants.GeopagosToumamentConstants;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GeopagosToumamentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HttpStatusCodeException.class)
    protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex){
        GeopagosToumamentError c4f = null;
        if(ex.getStatusCode().is4xxClientError()){
            c4f = new GeopagosToumamentError(ex.getStatusCode(),
                    GeopagosToumamentConstants.PREFIX_CLIENT_ERROR);
        }else if(ex.getStatusCode().is5xxServerError()){
            c4f = new GeopagosToumamentError(ex.getStatusCode(),
                    GeopagosToumamentConstants.PREFIX_SERVER_ERROR);
        }
        c4f.setMessage(ex.getMessage());
        log.error("Error HTTP Request Client: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }


    @ExceptionHandler(GeopagosToumamentEntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(GeopagosToumamentEntityNotFoundException ex) {
        GeopagosToumamentError c4f = new GeopagosToumamentError(HttpStatus.NOT_FOUND,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR + GeopagosToumamentConstants.NOT_FOUND);
        c4f.setMessage(ex.getMessage());
        log.error("Entity Not Found: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }


    @ExceptionHandler(GeopagosToumamentConflictException.class)
    protected ResponseEntity<Object> handleConflict(GeopagosToumamentConflictException ex) {
        GeopagosToumamentError c4f = new GeopagosToumamentError(HttpStatus.CONFLICT,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR + GeopagosToumamentConstants.CONFLICT);
        c4f.setMessage(ex.getMessage());
        log.error("Conflict: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(GeopagosToumamentEntityUnprocessableException.class)
    protected ResponseEntity<Object> handleUnprocessableEntity(GeopagosToumamentEntityUnprocessableException ex) {
        GeopagosToumamentError c4f = new GeopagosToumamentError(HttpStatus.UNPROCESSABLE_ENTITY,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR + GeopagosToumamentConstants.UNPROCESSABLE_ENTITY);
        c4f.setMessage(ex.getMessage());
        log.error("Unprocessable Entity: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(GeopagosToumamentUnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorized(GeopagosToumamentUnauthorizedException ex) {
        GeopagosToumamentError c4f = new GeopagosToumamentError(HttpStatus.UNAUTHORIZED,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR + GeopagosToumamentConstants.UNAUTHORIZED);
        c4f.setMessage(ex.getMessage());
        log.error("Unauthorized: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }


    @ExceptionHandler(GeopagosToumamentGenericServerException.class)
    protected ResponseEntity<Object> handleGenericServerError(GeopagosToumamentGenericServerException ex) {
        GeopagosToumamentError c4f = null;
        if(ex.getCode()!=null){
            c4f = new GeopagosToumamentError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getCode());
        }else{
            c4f = new GeopagosToumamentError(HttpStatus.INTERNAL_SERVER_ERROR,
                    GeopagosToumamentConstants.PREFIX_SERVER_ERROR + GeopagosToumamentConstants.INTERNAL_SERVER_ERROR);
        }
        c4f.setMessage(ex.getMessage());
        log.error("Internal Server Error: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        GeopagosToumamentError c4f = new GeopagosToumamentError(HttpStatus.BAD_REQUEST,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR + GeopagosToumamentConstants.BAD_REQUEST);
        c4f.setMessage(ex.getBindingResult().getFieldError().toString());
        c4f.setSubErrors(fillValidationErrorsFrom(ex));
        log.error("Bad Request: {}", ex.getBindingResult().getFieldError().toString());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(GeopagosToumamentGenericClientException.class)
    protected ResponseEntity<Object> handleGenericClientException(GeopagosToumamentGenericClientException ex) {
        GeopagosToumamentError c4f = new GeopagosToumamentError(ex.getHttpStatus(),
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR);
        c4f.setMessage(ex.getMessage());
        c4f.setSubErrors(ex.getSubErrors());
        log.error("Client Error: {}", ex.getMessage());
        return buildResponseEntity(c4f);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleError(Exception ex) {
        GeopagosToumamentError civilAgreementError = new GeopagosToumamentError(HttpStatus.INTERNAL_SERVER_ERROR,
                GeopagosToumamentConstants.PREFIX_SERVER_ERROR + GeopagosToumamentConstants.INTERNAL_SERVER_ERROR);
        civilAgreementError.setMessage("Error generico de servidor " + ex.getMessage());
        log.error("Server Error: {}", ex.getMessage());
        return buildResponseEntity(civilAgreementError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new GeopagosToumamentError(HttpStatus.BAD_REQUEST,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR + GeopagosToumamentConstants.BAD_REQUEST, error, ex));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status,
                                                                          WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        log.error("Bad Request: {}", error);
        return buildResponseEntity(new GeopagosToumamentError(HttpStatus.BAD_REQUEST,
                GeopagosToumamentConstants.PREFIX_CLIENT_ERROR + GeopagosToumamentConstants.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(GeopagosToumamentError civilAgreementError) {
        return new ResponseEntity<>(civilAgreementError, civilAgreementError.getHttpStatus());
    }

    protected List<GeopagosToumamentSubError> fillValidationErrorsFrom(MethodArgumentNotValidException argumentNotValid) {
        List<GeopagosToumamentSubError> subErrorCollection = new ArrayList<>();
        argumentNotValid.getBindingResult().getFieldErrors().get(0).getRejectedValue();
        argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
            GeopagosToumamentSubError civilAgreementSubError = new GeopagosToumamentValidationError(objError.getObjectName(),
                    objError.getField(), objError.getRejectedValue(), objError.getDefaultMessage());
            subErrorCollection.add(civilAgreementSubError);
        });
        return subErrorCollection;
    }

}
