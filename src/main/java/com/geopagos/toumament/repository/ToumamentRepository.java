package com.geopagos.toumament.repository;

import com.geopagos.toumament.model.Gender;
import com.geopagos.toumament.model.Toumament;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ToumamentRepository extends CrudRepository<Toumament,Integer> {
    Optional<Toumament> findToumamentByGenderAndState(Gender gender, Boolean state);
}
