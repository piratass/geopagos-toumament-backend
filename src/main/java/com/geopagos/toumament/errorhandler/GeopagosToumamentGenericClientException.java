package com.geopagos.toumament.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.List;

@Slf4j
public class GeopagosToumamentGenericClientException extends RuntimeException {

    private HttpStatus httpStatus;
    private List<GeopagosToumamentSubError> subErrors;
    private String logType;

    public GeopagosToumamentGenericClientException() {
        super();
    }

    public GeopagosToumamentGenericClientException(String message, Throwable ex) {
        super(message, ex);
    }

    public GeopagosToumamentGenericClientException(String message) {
        super(message);
    }

    public GeopagosToumamentGenericClientException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        log.error(message);
    }

    public GeopagosToumamentGenericClientException(String message, HttpStatus httpStatus,
                                                   List<GeopagosToumamentSubError> subErrors) {
        super(message);
        this.httpStatus = httpStatus;
        this.subErrors = subErrors;
        log.error(message);
    }

    public GeopagosToumamentGenericClientException(Throwable ex) {
        super(ex);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public List<GeopagosToumamentSubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<GeopagosToumamentSubError> subErrors) {
        this.subErrors = subErrors;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
