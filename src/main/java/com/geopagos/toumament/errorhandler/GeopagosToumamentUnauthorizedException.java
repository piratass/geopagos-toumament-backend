package com.geopagos.toumament.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeopagosToumamentUnauthorizedException extends RuntimeException {

    public GeopagosToumamentUnauthorizedException() {
        super();
    }

    public GeopagosToumamentUnauthorizedException(String message) {
        super(message);
        log.error(message);
    }

}
