package com.geopagos.toumament.controller;

import com.geopagos.toumament.constants.GeopagosToumamentConstants;
import com.geopagos.toumament.dto.PlayerResultResponseDTO;
import com.geopagos.toumament.dto.PlayersDataRequestDTO;
import com.geopagos.toumament.service.PlayerToumamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(GeopagosToumamentConstants.API_VERSION + GeopagosToumamentConstants.RESOURCE_TOUMAMENT_PLAYERS)
public class ToumamentPlayerController {
    final PlayerToumamentService playerToumamentService;
    @PostMapping(GeopagosToumamentConstants.RESOURCE_TOUMAMENT_PLAYERS_RESULT)
    public PlayerResultResponseDTO playerResultWin(@Valid @RequestBody List<PlayersDataRequestDTO> listPlayersDataRequestDTO) {
           return playerToumamentService.playerResultWin(listPlayersDataRequestDTO);
    }
}
