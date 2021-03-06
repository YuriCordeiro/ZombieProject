package br.com.zombie.controller;

import java.util.List;


import org.hibernate.service.spi.ServiceException;
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
import br.com.zombie.dto.LocalDTO;
import br.com.zombie.dto.SurvivorDTO;
import br.com.zombie.exception.DBException;
import br.com.zombie.exception.IdNotFoundException;
import br.com.zombie.exception.IsInfectedException;
import br.com.zombie.repository.ItemRepository;
import br.com.zombie.repository.SurvivorRepository;

@RestController
@RequestMapping(SurvivorController.SURVIVOR_BASE_URI)
public class SurvivorController {

	public static final String SURVIVOR_BASE_URI = "/survivors";

	private SurvivorRepository repository;
	
	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	public SurvivorController(SurvivorRepository repository) {
		this.repository = repository;
	}

	/**
	 * Insert a new Survivor
	 * 
	 * @param survivor
	 * @throws DBException
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public SurvivorDTO insert(@RequestBody SurvivorDTO survivor) {
		survivor.populateItems();
		if (survivor.getAmountOfInfectedWarnings() >= 3) {
			survivor.setInfected(true);
			System.out.println("The survivor " + survivor.getSurvivorName() + " is infected");
		}
		return repository.save(survivor);
	}

	/**
	 * Find a survivor by ID
	 * 
	 * @param id
	 * @return
	 * @throws DBException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) throws DBException {
		SurvivorDTO survivor = repository.findOne(id);
		if(survivor != null) {
			return new ResponseEntity<SurvivorDTO>(survivor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Survivor with respective id: " + id + " not found."), HttpStatus.NO_CONTENT);
		}
		
	}

	/**
	 * Remove a Survivor
	 * 
	 * @param id
	 * @throws IdNotFoundException
	 * @throws DBException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> remove(@PathVariable("id") Integer id) throws IdNotFoundException, DBException {

		SurvivorDTO survivor = repository.findOne(id);
		if (survivor != null) {
			repository.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Survivor with respective id: " + id + " not found."),
					HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Update a Survivor
	 * 
	 * @param survivor
	 * @throws DBException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody SurvivorDTO survivor)
			throws DBException {
		SurvivorDTO survivorResult = new SurvivorDTO();
		survivorResult = repository.findOne(id);

		if (survivorResult != null) {
			
			survivor.setSurvivorCode(id);
			if (survivor.getSurvivorInventory() == null) {
				survivor.setSurvivorInventory(survivorResult.getSurvivorInventory());
			}
			repository.save(survivor);
			return new ResponseEntity<>(survivor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new IdNotFoundException("Survivor with respective id: " + id + " not found."),
					HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<SurvivorDTO>> findAll() {
		return new ResponseEntity<List<SurvivorDTO>>(repository.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/trader/{tradingSurvivorCode}/item/{itemCode}/amount/{amountOfItemToTrade}/benefited/{survivorReceptorCode}",method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> tradeItem(@PathVariable("tradingSurvivorCode") Integer tradingSurvivorCode,
			@PathVariable("itemCode") Integer itemCode,
			@PathVariable("amountOfItemToTrade") Integer amountOfItemToTrade,
			@PathVariable("survivorReceptorCode") Integer survivorReceptorCode)
			throws IsInfectedException, IdNotFoundException, DBException {
		
		SurvivorDTO whosTrading = new SurvivorDTO();
		whosTrading = repository.findOne(tradingSurvivorCode); // Find for who's
															// trading the item

		if (whosTrading != null) {

			if (!isInfected(whosTrading)) { // If you're not infected, you can
											// trade items

				for (ItemDTO itemObj : whosTrading.getSurvivorInventory().getItems()) { // Looks
																						// for
																						// the
																						// item
																						// in
																						// the
																						// survivor
																						// repository

					if (itemObj.getItemCode().equals(itemCode)) { // If the item
																	// code
																	// parameter
																	// equals
																	// the item
																	// code on
																	// the
																	// survivor
																	// inventory
						if (itemObj.getItemAmount() >= amountOfItemToTrade) { // How
																				// many
																				// items
																				// of
																				// it
																				// do
																				// u
																				// have?
							
							Integer totalPrice = itemObj.getItemPrice()*amountOfItemToTrade;
							
							
							itemObj.setItemAmount(itemObj.getItemAmount() - amountOfItemToTrade); // changes
																									// the
																									// current
																									// amount
																									// of
																									// item
																									// of
																									// the
																									// survivor
																									// whosTrading

							SurvivorDTO survivorReceptor = new SurvivorDTO();

							survivorReceptor = repository.findOne(survivorReceptorCode);

							if (survivorReceptor != null) {

								if (!isInfected(survivorReceptor)) { // If
																		// receptor
																		// is
																		// not
																		// infected

									if (survivorReceptor.getPoints() >= totalPrice) { //If have points enough
										incrementReceptorSurvivorInventory(itemCode, survivorReceptor,
												amountOfItemToTrade);

										// If everything's ok, commit the
										// changes on
										// database
										repository.save(whosTrading);
										repository.save(survivorReceptor);
									} else {
										System.out.println("The survivor " + survivorReceptor.getSurvivorName() + " have not points enough");
										throw new ServiceException("The survivor " + survivorReceptor.getSurvivorName() + " have not points enough");
									}
								}

							} else {
								throw new IdNotFoundException(
										"Cannot find survivor with respective code: " + survivorReceptorCode);
							}
							break;
						}

					} else { // If Item doesn't exist
						throw new IdNotFoundException("Cannot find Item with respective code: " + itemCode);
					}
				}

			} else { // If survivor is infected: cannot trade items and cannot
						// acces inventory
				throw new IsInfectedException("The survivor " + whosTrading.getSurvivorName()
						+ " cannot trade items, cuz' (he's/she's) infected");
			}

			return new ResponseEntity<>(HttpStatus.OK);
			
		} else { // If whosTading equals null
			return new ResponseEntity<>(new IdNotFoundException("Survivor with respective id: " + tradingSurvivorCode + " not found."),
					HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Validates if a survivor is infected
	 * 
	 * @param survivorTO
	 * @return
	 */
	private boolean isInfected(SurvivorDTO survivorTO) {
		if (survivorTO.isInfected()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add new Item to another survivor
	 * 
	 * @param itemCode
	 * @param receptor
	 * @param amountOfItem
	 */
	private void incrementReceptorSurvivorInventory(Integer itemCode, SurvivorDTO receptor, Integer amountOfItem) {
		ItemDTO item = itemRepository.findOne(itemCode);

		for (ItemDTO itemObj : itemRepository.findAll()) {
			if (itemObj.getItemDescription().equals(item.getItemDescription())) {
				if (itemObj.getInventory().getInventoryCode().equals(item.getInventory().getInventoryCode())) {
					itemObj.setItemAmount(itemObj.getItemAmount() + amountOfItem);
					itemRepository.save(itemObj);
				}
			}
		}

	}

	/**
	 * Method updates the last local where survivor has sent any signal of life
	 * 
	 * @param survivorId
	 * @param lastSurvivorLocal
	 * @throws DBException
	 * @throws IdNotFoundException
	 */
	@RequestMapping(value = "/{survivorId}/update_location/{latitude}/{longitude}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateLastSurvivorLocal(@PathVariable("survivorId") Integer survivorId,
			@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude)
			throws DBException, IdNotFoundException {

		SurvivorDTO survivorObject = new SurvivorDTO();
		survivorObject = repository.findOne(survivorId);

		if (survivorObject != null) {
			
			LocalDTO local = new LocalDTO();
			local.setLocalCode(survivorObject.getLocal().getLocalCode());
			local.setLatitude(latitude);
			local.setLongitude(longitude);
			survivorObject.setLocal(local);
			repository.save(survivorObject);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(
					new IdNotFoundException("Survivor with respective code: {" + survivorId + "} not found"),
					HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/warnasinfected/{survivorId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> flagSurvivorAsInfected(@PathVariable("survivorId") Integer survivorId) throws DBException, IdNotFoundException {

		SurvivorDTO survivor = repository.findOne(survivorId); 

		if (survivor != null) {

			survivor.setAmountOfInfectedWarnings(survivor.getAmountOfInfectedWarnings() + 1);

			if (survivor.getAmountOfInfectedWarnings() >= 3) {
				survivor.setInfected(true);
				survivor.setPoints(survivor.getPoints() - 45);
				System.out.println("Survivor: " + survivor.getSurvivorName() + ", has been diagnosed as infected.");
			}

			repository.save(survivor);
			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new IdNotFoundException("Survivor with respective code: {" + survivorId + "} not found"), HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value = "/reports/percentages/infected", produces = MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET )
	public ResponseEntity<?> findAverageOfInfectedPlayers() {
		return new ResponseEntity<>(repository.findAVGInfected() * 100 + "%", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reports/percentages/non_infected", produces = MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET )
	public ResponseEntity<?> findAverageOfNonInfectedPlayers() {
		return new ResponseEntity<>(repository.findAVGNonInfected() * 100 + "%", HttpStatus.OK);
	}

}
