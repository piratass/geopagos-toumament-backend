package com.geopagos.toumament.service;

import com.geopagos.toumament.constants.GeopagosToumamentConstants;
import com.geopagos.toumament.dto.PlayerResultResponseDTO;
import com.geopagos.toumament.dto.PlayersDTO;
import com.geopagos.toumament.dto.PlayersDataRequestDTO;
import com.geopagos.toumament.dto.SkillDTO;
import com.geopagos.toumament.errorhandler.GeopagosToumamentGenericClientException;
import com.geopagos.toumament.model.*;
import com.geopagos.toumament.repository.*;
import com.geopagos.toumament.util.GeopagosToumamentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerToumamentServiceImpl implements PlayerToumamentService{
    final GenderRepository genderRepository;
    final GeopagosToumamentUtil geopagosToumamentUtil;
    final PlayerRepository playerRepository;
    final SkillRepository skillRepository;
    final PlayerSkillRepository playerSkillRepository;
    final ToumamentRepository toumamentRepository;
    final StageRepository stageRepository;
    final ToumamentPlayerRepository toumamentPlayerRepository;
    @Override
    public PlayerResultResponseDTO playerResultWin(List<PlayersDataRequestDTO> listPlayersDataRequestDTO) {
        /*playerSkillRepository.deleteAll();
        toumamentPlayerRepository.deleteAll();
        playerRepository.deleteAll();*/

        List<PlayersDTO> listPlayersM =new ArrayList<>();
        List<PlayersDTO> listPlayersF =new ArrayList<>();

        listPlayersDataRequestDTO.forEach(playerData->{
            Gender gender= genderRepository.findGenderByCodeAndState(playerData.getCodeGender(), GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                    new GeopagosToumamentGenericClientException("Error  atribute Genger List Player"));

           if (playerData.getCodeGender().equals(GeopagosToumamentConstants.GENDER_MALE)){
               if(playerData.getSkills().size()==2){
                   listPlayersM.add(geopagosToumamentUtil.convertObjPlayerDTO(playerData,gender));
               }
               else
                   throw new GeopagosToumamentGenericClientException("Error Skill Male conflict");
           }
           else {
               if(playerData.getSkills().size()==1) {
                  listPlayersF.add(geopagosToumamentUtil.convertObjPlayerDTO(playerData,gender));
               }
               else
                   throw new GeopagosToumamentGenericClientException("Error Skill Feminile conflict");
           }
        });
        //validation player and save tables
        if(geopagosToumamentUtil.validatePowTwo(listPlayersM.size())){
            listPlayersM.forEach(playerM->{
                Player player=playerRepository.save(geopagosToumamentUtil.convertEntityPlayer(playerM));

                for (SkillDTO skillDTO:playerM.getSkills()) {
                    Skill skill=skillRepository.findSkillByCodeAndState(skillDTO.getCodeSkill(),GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                            new GeopagosToumamentGenericClientException("Error  atribute Skills List Player"));
                    playerSkillRepository.save( geopagosToumamentUtil.convertEntityPlayerSkill(player,skill,skillDTO.getValue()));
                }
            });
        }else{
            throw new GeopagosToumamentGenericClientException("Error list player Male Not pow 2 ");
        }
        if(geopagosToumamentUtil.validatePowTwo(listPlayersF.size())){
            listPlayersF.forEach(playerF->{
                Player player=playerRepository.save(geopagosToumamentUtil.convertEntityPlayer(playerF));
                for (SkillDTO skillDTO:playerF.getSkills()) {
                    Skill skill=skillRepository.findSkillByCodeAndState(skillDTO.getCodeSkill(),GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                            new GeopagosToumamentGenericClientException("Error  atribute Skills List Player"));;
                    playerSkillRepository.save( geopagosToumamentUtil.convertEntityPlayerSkill(player,skill,skillDTO.getValue()));
                }
            });
        }else{
            throw new GeopagosToumamentGenericClientException("Error list player Feminile Not pow 2 ");
        }
        //operation win male and save table
        Gender genderMale= genderRepository.findGenderByCodeAndState(GeopagosToumamentConstants.GENDER_MALE, GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                new GeopagosToumamentGenericClientException("Error  atribute Genger List Player"));
        List<Player> listPlayerMale =playerRepository.findGenderByGenderAndState(genderMale,GeopagosToumamentConstants.RESOURCE_ACTIVE);
        Toumament toumamentMale =toumamentRepository.findToumamentByGenderAndState(genderMale,GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                new GeopagosToumamentGenericClientException("Error  atribute Genger List Player"));
        //execute Match
        iterationPlayer(listPlayerMale,toumamentMale,1);

        //operation win feminile and save table
        Gender genderFeminile= genderRepository.findGenderByCodeAndState(GeopagosToumamentConstants.GENDER_FEMININE, GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                new GeopagosToumamentGenericClientException("Error  atribute Genger List Player"));
        List<Player> listPlayerFeminile =playerRepository.findGenderByGenderAndState(genderFeminile,GeopagosToumamentConstants.RESOURCE_ACTIVE);
        Toumament toumamentFeminile =toumamentRepository.findToumamentByGenderAndState(genderFeminile,GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                new GeopagosToumamentGenericClientException("Error  atribute Genger List Player"));
        iterationPlayer(listPlayerFeminile,toumamentFeminile,1);

        //execute result data
        return null;
    }

    public void iterationPlayer(List<Player> listPlayer,Toumament toumament,Integer stage){
        Integer contPlayerFixture=1;
        Integer midPlayer=listPlayer.size()/2;

        if( midPlayer==1){
            Player player1=listPlayer.get(0);
            List<PlayerSkill> listPlayerSkill1 =playerSkillRepository.findPlayerSkillByPlayerAndState(player1,GeopagosToumamentConstants.RESOURCE_ACTIVE);
            Double sumSkill1 = listPlayerSkill1.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
            Double resultPlayer1= sumSkill1+ Math.random();

            Player player2=listPlayer.get(1);
            List<PlayerSkill> listPlayerSkill2 =playerSkillRepository.findPlayerSkillByPlayerAndState(player2,GeopagosToumamentConstants.RESOURCE_ACTIVE);
            Double sumSkill2 = listPlayerSkill2.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
            Double resultPlayer2= sumSkill2+ Math.random();

            if(resultPlayer1>resultPlayer2)
            {
                toumamentPlayerRepository.save(geopagosToumamentUtil.convertEntityToumament(player1,player2,toumament,player1.getIdPlayer(),stage,resultPlayer1,resultPlayer2));

            }else if(resultPlayer1==resultPlayer2){
                toumamentPlayerRepository.save(geopagosToumamentUtil.convertEntityToumament(player1,player2,toumament,player2.getIdPlayer(),stage,resultPlayer1,resultPlayer2));
            }
            else {
                toumamentPlayerRepository.save(geopagosToumamentUtil.convertEntityToumament(player1,player2,toumament,player2.getIdPlayer(),stage,resultPlayer1,resultPlayer2));
            }
        }

        else{
            while(contPlayerFixture!= midPlayer+1){
                Player player1=listPlayer.get(contPlayerFixture-1);
                List<PlayerSkill> listPlayerSkill1 =playerSkillRepository.findPlayerSkillByPlayerAndState(player1,GeopagosToumamentConstants.RESOURCE_ACTIVE);
                Double sumSkill1 = listPlayerSkill1.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
                Double resultPlayer1= sumSkill1+ Math.random();

                Player player2=listPlayer.get(listPlayer.size()-contPlayerFixture);
                List<PlayerSkill> listPlayerSkill2 =playerSkillRepository.findPlayerSkillByPlayerAndState(player2,GeopagosToumamentConstants.RESOURCE_ACTIVE);
                Double sumSkill2 = listPlayerSkill2.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
                Double resultPlayer2= sumSkill2+ Math.random();

                if(resultPlayer1>resultPlayer2)
                {
                    toumamentPlayerRepository.save(geopagosToumamentUtil.convertEntityToumament(player1,player2,toumament,player1.getIdPlayer(),stage,resultPlayer1,resultPlayer2));

                }else if(resultPlayer1==resultPlayer2){
                    toumamentPlayerRepository.save(geopagosToumamentUtil.convertEntityToumament(player1,player2,toumament,player2.getIdPlayer(),stage,resultPlayer1,resultPlayer2));
                }
                else {
                    toumamentPlayerRepository.save(geopagosToumamentUtil.convertEntityToumament(player1,player2,toumament,player2.getIdPlayer(),stage,resultPlayer1,resultPlayer2));
                }
                contPlayerFixture=contPlayerFixture+1;
            }
            List<ToumamentPlayer>toumamentPlayerList= toumamentPlayerRepository.findToumamentPlayerByIdStageAndToumament(stage,toumament);
            List<Player> playerList =new ArrayList<>();
            toumamentPlayerList.forEach(toumamentPlayer -> {
                Player player= playerRepository.findById(toumamentPlayer.getIdWinPlayer()).orElseThrow(() ->
                        new GeopagosToumamentGenericClientException("Error  atribute Genger List Player"));
                playerList.add(player);
            });
            iterationPlayer(playerList,toumament,stage+1);
        }
    }
}

