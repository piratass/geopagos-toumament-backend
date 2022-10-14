package com.geopagos.toumament.util;

import com.geopagos.toumament.constants.GeopagosToumamentConstants;
import com.geopagos.toumament.dto.PlayersDTO;
import com.geopagos.toumament.dto.PlayersDataRequestDTO;
import com.geopagos.toumament.errorhandler.GeopagosToumamentGenericServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geopagos.toumament.model.*;
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
        while (result!=true){
                if(numberBase==value){
                    result=true;
                }
                else if(numberBase>value){
                    break;
                }
                else{
                    numberBase= numberBase*2;
                }
        }
        return result;
    }
    public Player convertEntityPlayer(PlayersDTO playersDTO){
        Player player =new Player();
        player.setGender(playersDTO.getGender());
        player.setFullname(playersDTO.getFullname());
        player.setGender(playersDTO.getGender());
        player.setDocumentNumber(playersDTO.getDocumentNumber());
        player.setState(playersDTO.getState());
        player.setRegistrationDate(playersDTO.getRegistrationDate());
       return player;
    }
    public PlayerSkill convertEntityPlayerSkill(Player player, Skill skill,Double value){
        PlayerSkill playerSkill =new PlayerSkill();
        playerSkill.setPlayer(player);
        playerSkill.setSkill(skill);
        playerSkill.setLevelUpdate(value);
        playerSkill.setDescription("");
        playerSkill.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
        playerSkill.setRegistrationDate(new Date());
        return playerSkill;
    }

    public PlayersDTO convertObjPlayerDTO(PlayersDataRequestDTO playerData, Gender gender){
        PlayersDTO playerDTO =new PlayersDTO();
        playerDTO.setGender(gender);
        playerDTO.setFullname(playerData.getFullname());
        playerDTO.setDocumentNumber(playerData.getDocumentNumber());
        playerDTO.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
        playerDTO.setRegistrationDate(new Date());
        playerDTO.setSkills(playerData.getSkills());
        return playerDTO;
    }
    public ToumamentPlayer convertEntityToumament(Player player1,Player player2,Toumament toumament,Integer winResult,Integer stage,Double resultPlayer1,Double resultPlayer2 ){
        ToumamentPlayer toumamentPlayer =new ToumamentPlayer();
        toumamentPlayer.setPlayer1(player1);
        toumamentPlayer.setPlayer2(player2);
        toumamentPlayer.setToumament(toumament);
        toumamentPlayer.setIdStage(stage);
        toumamentPlayer.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
        toumamentPlayer.setRegistrationDate(new Date());
        toumamentPlayer.setIdWinPlayer(winResult);
        toumamentPlayer.setLocalResult(resultPlayer1.intValue());
        toumamentPlayer.setVisitResult(resultPlayer2.intValue());
        return toumamentPlayer;
    }


}
