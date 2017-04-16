package br.com.zombie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.zombie.dto.ItemDTO;
import br.com.zombie.exception.IdNotFoundException;
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
	public ResponseEntity<List<ItemDTO>> findAll() {
		return new ResponseEntity<List<ItemDTO>>(repository.findAll(), HttpStatus.OK);
	}

	/**
	 * Webservice mathod: find an item by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
		ItemDTO item = repository.findOne(id);
		if(item != null) {
			return new ResponseEntity<ItemDTO>(item, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Item with respective id: {" + id + "} not found.") ,HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * insert a new item
	 * 
	 * @param item
	 * @return 
	 */
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ItemDTO> insert(@RequestBody ItemDTO item) {
		return new ResponseEntity<ItemDTO>(repository.save(item),HttpStatus.CREATED);
	}

	/**
	 * remove a item by id
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<?> remove(@PathVariable("id") Integer id) {
		ItemDTO item = repository.findOne(id);
		if (item != null) {
			repository.delete(id);
			return new ResponseEntity<ItemDTO>(HttpStatus.OK);
		} else {
			return new ResponseEntity<IdNotFoundException>(HttpStatus.NO_CONTENT);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody ItemDTO item) {
		ItemDTO itemResult = repository.findOne(id);
		if (itemResult != null) {
			item.setItemCode(id);
			repository.save(item);
			return new ResponseEntity<ItemDTO>(HttpStatus.OK);
		} else {
			return new ResponseEntity(new IdNotFoundException("Unable to delete. Item with id " + id + " not found."),
					HttpStatus.NO_CONTENT);
		}
	}
	
}
