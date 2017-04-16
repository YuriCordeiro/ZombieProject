package br.com.zombie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zombie.dto.InventoryDTO;
import br.com.zombie.repository.InventoryRepository;

@RestController
@RequestMapping(InventoryController.INVENTORY_BASE_URI)
public class InventoryController {

	public static final String INVENTORY_BASE_URI = "/inventories";
	
	private InventoryRepository repository;
	
	@Autowired
	public InventoryController(InventoryRepository repository) {
		this.repository = repository;
	}

	/**
	 * Find all registered inventory
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<InventoryDTO> findAll() {
		return repository.findAll();
	}

	/**
	 * Webservice mathod: find an inventory by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public InventoryDTO findById(@PathVariable("id") Integer id) {
		return repository.findOne(id);
	}

	/**
	 * insert a new inventory
	 * 
	 * @param inventory
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public InventoryDTO insert(InventoryDTO inventory) {
		return repository.save(inventory);
	}

	/**
	 * remove a inventory by id
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void remove(@PathVariable("id") Integer id) {
		repository.delete(findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("id") Integer id, @RequestBody InventoryDTO inventory) {
		InventoryDTO inventoryResult = findById(id);
		if (inventoryResult != null) {
			repository.saveAndFlush(inventoryResult);
		}
	}
	
}
