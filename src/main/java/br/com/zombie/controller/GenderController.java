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

import br.com.zombie.dto.GenderDTO;
import br.com.zombie.exception.IdNotFoundException;
import br.com.zombie.repository.GenderRepository;

@RestController
@RequestMapping(GenderController.GENDER_BASE_URI)
public class GenderController {
	
	public static final String GENDER_BASE_URI = "/genders";

	private GenderRepository repository;
	

	/**
	 * Default GenderController Constructor
	 * 
	 * @param service
	 */
	@Autowired
	public GenderController(GenderRepository repository) {
		this.repository = repository;
	}

	/**
	 * Find all registered genders
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<GenderDTO>> findAll() {
		return new ResponseEntity<List<GenderDTO>>(repository.findAll(), HttpStatus.OK);
	}

	/**
	 * Webservice mathod: find an gender by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
		GenderDTO gender = repository.findOne(id);
		if (gender != null) {
			return new ResponseEntity<>(gender, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Gender with respective Id: {" + id + "} not found."), HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * insert a new gender
	 * 
	 * @param gender
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<GenderDTO> insert(@RequestBody GenderDTO gender) {
		return new ResponseEntity<GenderDTO>(repository.save(gender), HttpStatus.CREATED);
	}

	/**
	 * remove a gender by id
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> remove(@PathVariable("id") Integer id) {
		if (repository.findOne(id) != null) {
			repository.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Gender with respective Id: {" + id + "} not found."), HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody GenderDTO gender) {

		GenderDTO genderResult = repository.findOne(id);

		if (genderResult != null) {
			gender.setGenderId(id);
			repository.save(genderResult);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Gender with respective Id: {" + id + "} not found."),
					HttpStatus.NO_CONTENT);
		}
	}

}
