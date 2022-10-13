package com.geopagos.toumament.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeopagosToumamentConflictException extends RuntimeException {

    public GeopagosToumamentConflictException() {
        super();
    }

    public GeopagosToumamentConflictException(String message) {
        super(message);
        log.error(message);
    }


}