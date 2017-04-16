package br.com.zombie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zombie.dto.SurvivorDTO;

@Repository
public interface SurvivorRepository extends JpaRepository<SurvivorDTO, Integer> {

}
