package br.com.zombie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.zombie.dto.ItemDTO;

@Repository
public interface ItemRepository extends JpaRepository<ItemDTO, Integer>{
	
}
