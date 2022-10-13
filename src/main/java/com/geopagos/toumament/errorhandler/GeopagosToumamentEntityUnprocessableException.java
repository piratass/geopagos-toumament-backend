package com.geopagos.toumament.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeopagosToumamentEntityUnprocessableException extends RuntimeException {

    public GeopagosToumamentEntityUnprocessableException() {
        super();
    }

    public GeopagosToumamentEntityUnprocessableException(String message) {
        super(message);
        log.error(message);
    }
}
