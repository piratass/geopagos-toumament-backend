package com.geopagos.toumament.util;

import com.geopagos.toumament.errorhandler.GeopagosToumamentGenericServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.ContentHandler;
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
public class GeopagosToumamentUtil {
      public Boolean validateCodigoUsuario(String codigoUsuario) {
        return codigoUsuario != null;
    }

    public Boolean validatePowTwo(Integer value) {
        Boolean result=false;
        Integer numberBase=2;
        while (result==true){
                if(numberBase==value){
                    result=true;
                }else if(numberBase>value){
                    break;
                }else{
                    numberBase= numberBase*2;
                }
        }
        return result;
    }
}
