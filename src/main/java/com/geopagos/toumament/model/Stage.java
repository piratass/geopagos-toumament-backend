package com.geopagos.toumament.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="T_STAGE")
@Data
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idstage")
    private Integer idStage;
    @Column(name="code")
    private String code;
    @Column(name="namestage")
    private String nameStage;
    @Column(name="state")
    private Integer state;
    @Column(name="registrationDate")
    private Date registrationDate;
}
