package com.geopagos.toumament.util;

import com.geopagos.toumament.errorhandler.GeopagosToumamentGenericServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class C4fUtil {
      public Boolean validateCodigoUsuario(String codigoUsuario) {
        return codigoUsuario != null;
    }
    public static void printUriRequestJson(String location, String uri, Object object) {
        try {
            String url="url";
            String simbol="=========";
            String jsonString="json";
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(simbol, location,simbol);
            log.info(url , uri);
            if (object != null)
            {
                log.info(jsonString , objectMapper.writeValueAsString(object));
            }
            log.info(simbol);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    public static Optional<URI> createURI(String urlAsString) {
        URI uri;
        try {
            uri = new URI(urlAsString);
        } catch (URISyntaxException uriEx) {
            throw new GeopagosToumamentGenericServerException("Error in TI URL");
        }
        return Optional.of(uri);
    }
    public static <T> void manageRestTemplate(ResponseEntity<T> responseEntity) throws GeopagosToumamentGenericServerException {
        if (responseEntity != null) {
            switch (responseEntity.getStatusCode()) {
                case OK:
                    break;
                case INTERNAL_SERVER_ERROR:
                    throw new GeopagosToumamentGenericServerException("INTERNAL_SERVER_ERROR");
                case FORBIDDEN:
                    throw new GeopagosToumamentGenericServerException("FORBIDDEN");
                case UNAUTHORIZED:
                    throw new GeopagosToumamentGenericServerException("UNAUTHORIZED");
                default:
                    throw new GeopagosToumamentGenericServerException(responseEntity.getStatusCode() +
                            " Ocurrio un error generico consultado al servicio");
            }
        }
    }
    public Boolean validateCellphone(String cellphone) {
        if (cellphone != null) {
            if (!cellphone.matches("\\d{9}")) return false;
            else {
                Pattern pattern = Pattern.compile("^9.*");
                Matcher matcher = pattern.matcher(cellphone);
                return matcher.matches();
            }
        }
        return false;
    }
}
