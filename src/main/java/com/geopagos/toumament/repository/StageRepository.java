package com.geopagos.toumament.repository;

import com.geopagos.toumament.model.Gender;
import com.geopagos.toumament.model.Stage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StageRepository extends CrudRepository<Stage,Integer> {
    Optional<Stage> findStageByCodeAndState(String Code, Boolean state);
}
