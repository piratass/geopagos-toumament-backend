package com.geopagos.toumament.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_toumament_player")
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
    private Integer idStage;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idplayer1")
    private Player player1;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idplayer2")
    private Player player2;
    @Column(name="idwinplayer")
    private Integer idWinPlayer;
    @Column(name="localresult")
    private Integer localResult;
    @Column(name="visitresult")
    private Integer visitResult;
    @Column(name="ischampions")
    private Boolean isChampions;
    @Column(name="state")
    private Boolean state;
    @Column(name="registrationDate")
    private Date registrationDate;
}
