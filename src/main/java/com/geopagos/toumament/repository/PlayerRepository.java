package com.geopagos.toumament.repository;

import com.geopagos.toumament.model.Gender;
import com.geopagos.toumament.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player,Integer> {
    List<Player> findPlayerByGenderAndState(Gender gender, Boolean state);
    List<Player> findByState(Boolean state);

}
