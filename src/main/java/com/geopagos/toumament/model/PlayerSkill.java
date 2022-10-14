package com.geopagos.toumament.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="T_PLAYER_SKILL")
@Data
public class PlayerSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idplayerskill")
    private Integer idPlayerSkill;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idPlayer")
    private Player player;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idskill")
    private Skill skill;
    @Column(name="levelupdate")
    private double levelUpdate;
    @Column(name="description")
    private String description;
    @Column(name="state")
    private Boolean state;
    @Column(name="registrationDate")
    private Date registrationDate;
}
