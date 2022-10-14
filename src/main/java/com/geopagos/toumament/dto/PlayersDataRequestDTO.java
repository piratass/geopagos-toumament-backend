package com.geopagos.toumament.dto;

import lombok.Data;

import java.util.List;

@Data

public class PlayersDataRequestDTO {
    private String fullname;
    private String documentNumber;
    private String codeGender;
    private List<SkillDTO> skills;

}
