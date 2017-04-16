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

import br.com.zombie.dto.LocalDTO;
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
	public List<LocalDTO> findAll() {
		return repository.findAll();
	}

	/**
	 * Webservice mathod: find an local by id
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public LocalDTO findById(@PathVariable("id") Integer id) {
		return repository.findOne(id);
	}

	/**
	 * insert a new local
	 * 
	 * @param local
	 */
	@RequestMapping(method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void insert(@RequestBody LocalDTO local) {
		repository.save(local);
	}

	/**
	 * remove a local by id
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
	public void update(@PathVariable("id") Integer id, @RequestBody LocalDTO local) {
		LocalDTO localResult = findById(id);
		if (localResult != null) {
			repository.saveAndFlush(localResult);
		}
	}
	
}
