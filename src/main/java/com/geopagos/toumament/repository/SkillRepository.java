package com.geopagos.toumament.repository;

import com.geopagos.toumament.model.Gender;
import com.geopagos.toumament.model.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SkillRepository extends CrudRepository<Skill,Integer> {
    Optional<Skill> findSkillByCodeAndState(String Code, Boolean state);
}
