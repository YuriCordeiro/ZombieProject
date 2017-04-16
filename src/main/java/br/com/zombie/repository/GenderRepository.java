package br.com.zombie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zombie.dto.GenderDTO;

@Repository
public interface GenderRepository extends JpaRepository<GenderDTO, Integer> {

}
