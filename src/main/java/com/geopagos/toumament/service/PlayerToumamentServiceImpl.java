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
        if(listPlayersDataRequestDTO!=null)
            throw new GeopagosToumamentGenericClientException("Not found List player");
        playerSkillRepository.deleteAll();
        playerRepository.deleteAll();

        List<PlayersDTO> listPlayersM =new ArrayList<>();
        List<PlayersDTO> listPlayersF =new ArrayList<>();

        listPlayersDataRequestDTO.forEach(playerData->{
            Gender gender= genderRepository.findGenderByCodeAndState(playerData.getCodeGender(), GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                    new GeopagosToumamentGenericClientException("Error  atribute Genger List Player"));
            PlayersDTO player =new PlayersDTO();
            player.setGender(gender);
           if (playerData.getCodeGender().equals(GeopagosToumamentConstants.GENDER_MALE)){
               if(playerData.getSkills().size()==2){
                    player.setFullname(playerData.getFullname());
                    player.setDocumentNumber(playerData.getDocumentNumber());
                    player.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
                    player.setRegistrationDate(new Date());
                    player.setSkills(playerData.getSkills());
                    listPlayersM.add(player);
               }
               else
                   throw new GeopagosToumamentGenericClientException("Error Skill Male conflict");
           }
           else {
               if(playerData.getSkills().size()==1) {
                   player.setFullname(playerData.getFullname());
                   player.setDocumentNumber(playerData.getDocumentNumber());
                   player.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
                   player.setRegistrationDate(new Date());
                   player.setSkills(playerData.getSkills());
                   listPlayersF.add(player);
               }
               else
                   throw new GeopagosToumamentGenericClientException("Error Skill Feminile conflict");
           }
        });
        //validation player and save tables
        if(geopagosToumamentUtil.validatePowTwo(listPlayersM.size())){
            listPlayersM.forEach(playerM->{
                Player player =new Player();
                player.setFullname(playerM.getFullname());
                player.setGender(playerM.getGender());
                player.setDocumentNumber(playerM.getDocumentNumber());
                player.setState(playerM.getState());
                player.setRegistrationDate(playerM.getRegistrationDate());
                player=playerRepository.save(player);
                for (SkillDTO skillDTO:playerM.getSkills()) {
                    Skill skill=skillRepository.findSkillByCodeAndState(skillDTO.getCodeSkill(),GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                            new GeopagosToumamentGenericClientException("Error  atribute Skills List Player"));;
                    PlayerSkill playerSkill =new PlayerSkill();
                    playerSkill.setPlayer(player);
                    playerSkill.setSkill(skill);
                    playerSkill.setLevelUpdate(skillDTO.getValue());
                    playerSkill.setDescription("");
                    playerSkill.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
                    playerSkill.setRegistrationDate(new Date());
                    playerSkillRepository.save(playerSkill);
                }
            });


        }else{
            throw new GeopagosToumamentGenericClientException("Error list player Male Not pow 2 ");
        }
        if(geopagosToumamentUtil.validatePowTwo(listPlayersF.size())){
            listPlayersF.forEach(playerF->{
                Player player =new Player();
                player.setFullname(playerF.getFullname());
                player.setGender(playerF.getGender());
                player.setDocumentNumber(playerF.getDocumentNumber());
                player.setState(playerF.getState());
                player.setRegistrationDate(playerF.getRegistrationDate());
                player=playerRepository.save(player);
                for (SkillDTO skillDTO:playerF.getSkills()) {
                    Skill skill=skillRepository.findSkillByCodeAndState(skillDTO.getCodeSkill(),GeopagosToumamentConstants.RESOURCE_ACTIVE).orElseThrow(() ->
                            new GeopagosToumamentGenericClientException("Error  atribute Skills List Player"));;
                    PlayerSkill playerSkill =new PlayerSkill();
                    playerSkill.setPlayer(player);
                    playerSkill.setSkill(skill);
                    playerSkill.setLevelUpdate(skillDTO.getValue());
                    playerSkill.setDescription("");
                    playerSkill.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
                    playerSkill.setRegistrationDate(new Date());
                    playerSkillRepository.save(playerSkill);
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
        return null;
    }

    public void iterationPlayer(List<Player> listPlayer,Toumament toumament,Integer stage){
        Integer contPlayerFixture=1;
        Integer midPlayer=listPlayer.size()/2;

        if( midPlayer==1){
            ToumamentPlayer toumamentPlayer = new ToumamentPlayer();
            Player player1=listPlayer.get(0);
            List<PlayerSkill> listPlayerSkill1 =playerSkillRepository.findPlayerSkillByPlayerAndState(player1,GeopagosToumamentConstants.RESOURCE_ACTIVE);
            Double sumSkill1 = listPlayerSkill1.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
            Double resultPlayer1= sumSkill1+ Math.random();

            Player player2=listPlayer.get(1);
            List<PlayerSkill> listPlayerSkill2 =playerSkillRepository.findPlayerSkillByPlayerAndState(player2,GeopagosToumamentConstants.RESOURCE_ACTIVE);
            Double sumSkill2 = listPlayerSkill2.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
            Double resultPlayer2= sumSkill2+ Math.random();


            toumamentPlayer.setPlayer1(player1);
            toumamentPlayer.setPlayer2(player2);
            toumamentPlayer.setToumament(toumament);
            toumamentPlayer.setStage(stage);
            toumamentPlayer.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
            toumamentPlayer.setRegistrationDate(new Date());
            if(resultPlayer1>resultPlayer2)
            {
                toumamentPlayer.setIdWinPlayer(player1.getIdPlayer());
                toumamentPlayer.setLocalResult(resultPlayer1.intValue());
                toumamentPlayer.setVisitResult(resultPlayer2.intValue());
                toumamentPlayerRepository.save(toumamentPlayer);

            }
            if(resultPlayer1==resultPlayer2){
                toumamentPlayer.setIdWinPlayer(player2.getIdPlayer());
                toumamentPlayer.setLocalResult(resultPlayer1.intValue());
                toumamentPlayer.setVisitResult(resultPlayer2.intValue()+1);
                toumamentPlayerRepository.save(toumamentPlayer);
            }
            else {
                toumamentPlayer.setIdWinPlayer(player2.getIdPlayer());
                toumamentPlayer.setLocalResult(resultPlayer1.intValue());
                toumamentPlayer.setVisitResult(resultPlayer2.intValue());
                toumamentPlayerRepository.save(toumamentPlayer);
            }
        }

        else{
            while(contPlayerFixture!= midPlayer){
                ToumamentPlayer toumamentPlayer = new ToumamentPlayer();
                Player player1=listPlayer.get(contPlayerFixture-1);
                List<PlayerSkill> listPlayerSkill1 =playerSkillRepository.findPlayerSkillByPlayerAndState(player1,GeopagosToumamentConstants.RESOURCE_ACTIVE);
                Double sumSkill1 = listPlayerSkill1.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
                Double resultPlayer1= sumSkill1+ Math.random();

                Player player2=listPlayer.get(listPlayer.size()-contPlayerFixture);
                List<PlayerSkill> listPlayerSkill2 =playerSkillRepository.findPlayerSkillByPlayerAndState(player2,GeopagosToumamentConstants.RESOURCE_ACTIVE);
                Double sumSkill2 = listPlayerSkill2.stream().mapToDouble(PlayerSkill::getLevelUpdate).sum();
                Double resultPlayer2= sumSkill2+ Math.random();

                toumamentPlayer.setPlayer1(player1);
                toumamentPlayer.setPlayer2(player2);
                toumamentPlayer.setToumament(toumament);
                toumamentPlayer.setStage(stage);
                toumamentPlayer.setState(GeopagosToumamentConstants.RESOURCE_ACTIVE);
                toumamentPlayer.setRegistrationDate(new Date());
                if(resultPlayer1>resultPlayer2)
                {
                    toumamentPlayer.setIdWinPlayer(player1.getIdPlayer());
                    toumamentPlayer.setLocalResult(resultPlayer1.intValue());
                    toumamentPlayer.setVisitResult(resultPlayer2.intValue());
                    toumamentPlayerRepository.save(toumamentPlayer);

                }
                if(resultPlayer1==resultPlayer2){
                    toumamentPlayer.setIdWinPlayer(player2.getIdPlayer());
                    toumamentPlayer.setLocalResult(resultPlayer1.intValue());
                    toumamentPlayer.setVisitResult(resultPlayer2.intValue()+1);
                    toumamentPlayerRepository.save(toumamentPlayer);
                }
                else {
                    toumamentPlayer.setIdWinPlayer(player2.getIdPlayer());
                    toumamentPlayer.setLocalResult(resultPlayer1.intValue());
                    toumamentPlayer.setVisitResult(resultPlayer2.intValue());
                    toumamentPlayerRepository.save(toumamentPlayer);
                }
                contPlayerFixture=contPlayerFixture+1;
            }
            List<ToumamentPlayer>toumamentPlayerList= toumamentPlayerRepository.findToumamentPlayerByStageAndToumament(stage,toumament);
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

