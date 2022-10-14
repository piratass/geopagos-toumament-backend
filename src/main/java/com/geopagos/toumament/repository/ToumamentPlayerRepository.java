package com.geopagos.toumament.repository;

import com.geopagos.toumament.model.Toumament;
import com.geopagos.toumament.model.ToumamentPlayer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToumamentPlayerRepository extends CrudRepository<ToumamentPlayer,Integer> {

    List<ToumamentPlayer> findToumamentPlayerByIdStageAndToumament(Integer stage, Toumament toumament);
}
