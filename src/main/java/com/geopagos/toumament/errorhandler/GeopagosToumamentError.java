package com.geopagos.toumament.errorhandler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;
@Data
public class GeopagosToumamentError {

    private String code;
    private HttpStatus httpStatus;
    private Date timestamp;
    private String message;
    private String debugMessage;
    private List<GeopagosToumamentSubError> subErrors;

    private GeopagosToumamentError() {
        timestamp = new Date();
    }

    public GeopagosToumamentError(HttpStatus httpStatus, String code) {
        this();
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public GeopagosToumamentError(HttpStatus httpStatus, String code, Throwable e) {
        this();
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = "Unexpected error";
        this.debugMessage = e.getLocalizedMessage();
    }

    public GeopagosToumamentError(HttpStatus httpStatus, String code, String message, Throwable e) {
        this();
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = e.getLocalizedMessage();
    }
}
