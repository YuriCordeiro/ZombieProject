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

import br.com.zombie.dto.ItemDTO;
import br.com.zombie.repository.ItemRepository;

@RestController
@RequestMapping(ItemController.INVENTORY_BASA_URI)
public class ItemController {

	public static final String INVENTORY_BASA_URI = "/items";
	
	private ItemRepository repository;
	
	@Autowired
	public ItemController(ItemRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Find all registered item
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ItemDTO> findAll() {
		return repository.findAll();
	}

	/**
	 * Webservice mathod: find an item by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ItemDTO findById(@PathVariable("id") Integer id) {
		return repository.findOne(id);
	}

	/**
	 * insert a new item
	 * 
	 * @param item
	 */
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void insert(@RequestBody ItemDTO item) {
		repository.save(item);
	}

	/**
	 * remove a item by id
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
	public void update(@PathVariable("id") Integer id, @RequestBody ItemDTO item) {
		ItemDTO itemResult = findById(id);
		if (itemResult != null) {
			repository.saveAndFlush(itemResult);
		}
	}
	
}
