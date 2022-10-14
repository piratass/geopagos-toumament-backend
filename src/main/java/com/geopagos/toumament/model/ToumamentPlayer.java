package com.geopagos.toumament.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="T_TOUMAMENT_PLAYER")
@Data
public class ToumamentPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idtoumamentplayer")
    private Integer idToumamentPlayer;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idtoumament")
    private Toumament toumament;
    @JoinColumn(name="idstage")
    private Integer stage;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idplayer1")
    private Player player1;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idplayer2")
    private Player player2;
    private Integer idWinPlayer;
    @Column(name="localresult")
    private Integer localResult;
    @Column(name="visitresult")
    private Integer visitResult;
    @Column(name="state")
    private Boolean state;
    @Column(name="registrationDate")
    private Date registrationDate;
}
