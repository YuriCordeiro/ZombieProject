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

import br.com.zombie.dto.GenderDTO;
import br.com.zombie.service.GenderRepository;

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
	public List<GenderDTO> findAll() {
		return repository.findAll();
	}

	/**
	 * Webservice mathod: find an gender by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GenderDTO findById(@PathVariable("id") Integer id) {
		return repository.findOne(id);
	}

	/**
	 * insert a new gender
	 * 
	 * @param gender
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void insert(@RequestBody GenderDTO gender) {
		repository.saveAndFlush(gender);
	}

	/**
	 * remove a gender by id
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
	public void update(@PathVariable("id") Integer id, @RequestBody GenderDTO gender) {
		GenderDTO genderResult = findById(id);
		if (genderResult != null) {
			repository.saveAndFlush(genderResult);
		}
	}

}
