package com.geopagos.toumament.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="T_PLAYER")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idplayer")
    private Integer idPlayer;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idgender")
    private Gender gender;
    @Column(name="documentnumber")
    private String documentNumber;
    @Column(name="fullname")
    private String fullname;
    @Column(name="state")
    private Integer state;
    @Column(name="registrationDate")
    private Date registrationDate;
}
