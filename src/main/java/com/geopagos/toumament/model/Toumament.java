package com.geopagos.toumament.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="T_TOUMAMENT")
@Data
public class Toumament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idtoumament")
    private Integer idToumament;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idGender")
    private Gender gender;
    @Column(name="code")
    private String code;
    @Column(name="nametoumament")
    private String nameToumament;
    @Column(name="state")
    private Boolean state;
    @Column(name="registrationDate")
    private Date registrationDate;
}
