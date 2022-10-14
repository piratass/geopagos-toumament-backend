package com.geopagos.toumament.repository;

import com.geopagos.toumament.model.Gender;
import com.geopagos.toumament.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player,Integer> {
    List<Player> findGenderByGenderAndState(Gender gender, Boolean state);

}
