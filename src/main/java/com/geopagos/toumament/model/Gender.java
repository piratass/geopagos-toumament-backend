package com.geopagos.toumament.model;
import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.Date;

@Entity
@Table(name="t_gender")
@Data
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idgender")
    private Integer idGender;
    @Column(name="code")
    private String code;
    @Column(name="namegender")
    private String nameGender;
    @Column(name="state")
    private Boolean state;
    @Column(name="registrationDate")
    private Date registrationDate;
}

