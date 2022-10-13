package com.geopagos.toumament.errorhandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeopagosToumamentEntityNotFoundException extends RuntimeException {


	public GeopagosToumamentEntityNotFoundException() {
		super();
	}

	public GeopagosToumamentEntityNotFoundException(String message) {
		super(message);
		log.error(message);
	}
}
