package com.geopagos.toumament.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="T_SKILL")
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idskill")
    private Integer idSkill;
    @Column(name="code")
    private String code;
    @Column(name="nameskill")
    private String nameSkill;
    @Column(name="state")
    private Integer state;
    @Column(name="registrationDate")
    private Date registrationDate;

}
