package br.com.zombie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zombie.dto.InventoryDTO;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryDTO, Integer> {

}
