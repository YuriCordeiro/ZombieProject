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
import org.springframework.web.bind.annotation.RestController;

import br.com.zombie.dto.LocalDTO;
import br.com.zombie.exception.IdNotFoundException;
import br.com.zombie.repository.LocalRepository;

@RestController
@RequestMapping(LocalController.LOCAL_BASE_URI)
public class LocalController {

	public static final String LOCAL_BASE_URI = "/locals";
	
	private LocalRepository repository;
	
	@Autowired
	public LocalController(LocalRepository repository) {
		this.repository = repository;
	}
	
	/**
	 * Find all registered local
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<LocalDTO>> findAll() {
		return new ResponseEntity<List<LocalDTO>>(repository.findAll(), HttpStatus.OK);
	}

	/**
	 * Webservice mathod: find an local by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
		LocalDTO local = repository.findOne(id);
		if(local != null){
			return new ResponseEntity<LocalDTO>(local, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Local with respective id: {" + id + "} not found."), HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * insert a new local
	 * 
	 * @param local
	 */
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<LocalDTO> insert(@RequestBody LocalDTO local) {
		return new ResponseEntity<LocalDTO>(repository.save(local), HttpStatus.CREATED);
	}

	/**
	 * remove a local by id
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> remove(@PathVariable("id") Integer id) {

		LocalDTO local = repository.findOne(id);

		if (local != null) {
			repository.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Local with respective id: {" + id + "} not found."),
					HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody LocalDTO local) {
		LocalDTO localResult = repository.findOne(id);
		if (localResult != null) {
			local.setLocalCode(id);
			repository.save(local);
			return new ResponseEntity<LocalDTO>(local, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Local with respective id: {" + id + "} not found."),
					HttpStatus.NO_CONTENT);
		}
	}
	
}
