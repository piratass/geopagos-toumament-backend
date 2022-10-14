package com.geopagos.toumament.repository;

import com.geopagos.toumament.model.Gender;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GenderRepository  extends CrudRepository<Gender,Integer> {
    Optional<Gender> findGenderByCodeAndState(String Code,Boolean state);
}
