package com.geopagos.toumament.repository;


import com.geopagos.toumament.model.Player;
import com.geopagos.toumament.model.PlayerSkill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerSkillRepository extends CrudRepository<PlayerSkill,Integer> {
    List<PlayerSkill> findPlayerSkillByPlayerAndState(Player player, Boolean state);
}
