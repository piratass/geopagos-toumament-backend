package com.geopagos.toumament.errorhandler;

import lombok.Data;

@Data
public class GeopagosToumamentValidationError extends GeopagosToumamentSubError {
    private String object;
    private String field;
    private Object rejectValue;
    private String message;

    public GeopagosToumamentValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public GeopagosToumamentValidationError(String object, String field, Object rejectValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectValue = rejectValue;
        this.message = message;
    }
}
