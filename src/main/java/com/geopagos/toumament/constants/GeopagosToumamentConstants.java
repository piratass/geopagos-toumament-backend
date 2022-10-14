package com.geopagos.toumament.constants;

public class GeopagosToumamentConstants {
    private GeopagosToumamentConstants(){}
    public static final String API_VERSION = "v1";
    // ERRORS
    public static final String PREFIX_SERVER_ERROR = "SRV";
    public static final String PREFIX_CLIENT_ERROR = "CLI";
    // CLIENT ERRORS
    public static final String UNPROCESSABLE_ENTITY =  "003";
    public static final String BAD_REQUEST = "001";
    public static final String UNAUTHORIZED = "002";
    public static final String NOT_FOUND = "004";
    public static final String CONFLICT = "009";
    public static final String GENDER_MALE = "MASCULINO";
    public static final String GENDER_FEMININE = "FEMENINO";
    public static final String STAGE_CODE_COMPETENCIA = "COMPETENCIA";
    public static final String STAGE_CODE_FINAL = "FINAL";
    // SERVER ERRORS
    public static final String INTERNAL_SERVER_ERROR = "001";

    public static final Boolean RESOURCE_ACTIVE= Boolean.TRUE;

    public static final String RESOURCE_TOUMAMENT_PLAYERS = "/toumament-players";
    public static final String RESOURCE_TOUMAMENT_PLAYERS_RESULT = "/result";

}
