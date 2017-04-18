package br.com.zombie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.zombie.dto.SurvivorDTO;

@Repository
public interface SurvivorRepository extends JpaRepository<SurvivorDTO, Integer> {

	@Query("SELECT AVG(survivor.infected) FROM SurvivorDTO survivor")
	public Long findAVGInfected();
	
	@Query("SELECT AVG(survivor.infected) FROM SurvivorDTO survivor WHERE survivor.infected = 0")
	public Long findAVGNonInfected();
	
}
