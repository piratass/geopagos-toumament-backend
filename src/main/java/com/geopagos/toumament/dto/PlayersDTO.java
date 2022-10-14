package com.geopagos.toumament.dto;

import com.geopagos.toumament.model.Gender;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlayersDTO {

    private Gender gender;
    private String documentNumber;
    private String fullname;
    private Boolean state;
    private Date registrationDate;
    private List<SkillDTO> skills;
}
