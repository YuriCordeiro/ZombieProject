package br.com.zombie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zombie.dto.LocalDTO;

@Repository
public interface LocalRepository extends JpaRepository<LocalDTO, Integer> {
}
