package com.geopagos.toumament.service;

import com.geopagos.toumament.dto.PlayerResultResponseDTO;
import com.geopagos.toumament.dto.PlayersDataRequestDTO;

import java.util.List;

public interface PlayerToumamentService {
    PlayerResultResponseDTO playerResultWin(List<PlayersDataRequestDTO> listPlayersDataRequestDTO);
}
