package com.geopagos.toumament.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeopagosToumamentGenericServerException extends RuntimeException {

    private String code;

    public GeopagosToumamentGenericServerException() {
        super();
    }

    public GeopagosToumamentGenericServerException(String message) {
        super(message);
        log.error(message);
    }

    public GeopagosToumamentGenericServerException(String message, String code) {
        super(message);
        this.code = code;
        log.error(message);
    }

    public GeopagosToumamentGenericServerException(Throwable ex) {
        super(ex);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
