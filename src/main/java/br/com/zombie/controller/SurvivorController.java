package br.com.zombie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
import br.com.zombie.repository.SurvivorRepository;

@RestController
@RequestMapping(SurvivorController.SURVIVOR_BASE_URI)
public class SurvivorController {

	public static final String SURVIVOR_BASE_URI = "/survivors";

	private SurvivorRepository repository;

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
	@ResponseBody
	public void insert(SurvivorDTO survivor) throws DBException {
		repository.saveAndFlush(survivor);
	}

	/**
	 * Find a survivr by ID
	 * 
	 * @param id
	 * @return
	 * @throws DBException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public SurvivorDTO findById(@PathVariable("id") Integer id) throws DBException {
		return repository.findOne(id);
	}

	/**
	 * Remove a Survivor
	 * 
	 * @param id
	 * @throws IdNotFoundException
	 * @throws DBException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void remove(@PathVariable("id") Integer id) throws IdNotFoundException, DBException {
		repository.delete(findById(id));
	}

	/**
	 * Update a Survivor
	 * 
	 * @param survivor
	 * @throws DBException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void update(Integer id) throws DBException {
		SurvivorDTO survivorResult = new SurvivorDTO();
		survivorResult = findById(id);

		repository.saveAndFlush(survivorResult);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<SurvivorDTO> findAll() {
		return repository.findAll();
	}

	@RequestMapping(value = "/trader/{tradingSurivorCode}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void tradeItem(@PathVariable("tradingSurvivorCode") Integer tradingSurvivorCode, Integer itemCode, Integer amountOfItemToTrade, Integer survivorReceptorCode)
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

									incrementReceptorSurvivorInventory(itemCode, survivorReceptor, amountOfItemToTrade);

									// If everything's ok, commit the changes on
									// database
									repository.saveAndFlush(whosTrading);
									repository.saveAndFlush(survivorReceptor);
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

		} else { // If whosTading equals null
			throw new IdNotFoundException("Cannot find survivor with respective code: " + tradingSurvivorCode);
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
		for (ItemDTO itemObj : receptor.getSurvivorInventory().getItems()) {
			if (itemObj.getItemCode().equals(itemCode)) { // (He/she) have this
															// item: then we add
				itemObj.setItemAmount(itemObj.getItemAmount() + amountOfItem);
				break;
			}
		}
	}

	/**
	 * Method updates the last local where survivor has sent any signal of life
	 * 
	 * @param survivorCode
	 * @param lastSurvivorLocal
	 * @throws DBException
	 * @throws IdNotFoundException
	 */
	@RequestMapping(value="/lastLocal/survivor/{survivorCode}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void updateLastSurvivorLocal(Integer survivorCode, String latitude, String longitude)
			throws DBException, IdNotFoundException {

		SurvivorDTO survivorObject = new SurvivorDTO();

		LocalDTO lastSurvivorLocal = new LocalDTO();
		lastSurvivorLocal.setLatitude(latitude);
		lastSurvivorLocal.setLongitude(longitude);

		survivorObject = repository.findOne(survivorCode);

		if (survivorObject != null) {
			survivorObject.setSurvivorLastLocal(lastSurvivorLocal);
			repository.saveAndFlush(survivorObject);
		} else {
			throw new IdNotFoundException("Survivor with respective code: '" + survivorCode + "' not found.");
		}
	}

	@RequestMapping(value = "/warninfect/{survivorCode}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void flagSurvivorAsInfected(Integer survivorCode) throws DBException, IdNotFoundException {

		SurvivorDTO survivor = new SurvivorDTO();

		survivor = repository.findOne(survivorCode);
		if (survivor != null) {

			survivor.setAmountOfInfectedWarnings(survivor.getAmountOfInfectedWarnings() + 1);

			if (survivor.getAmountOfInfectedWarnings() >= 3) {
				survivor.setInfected(true);
			}

			repository.saveAndFlush(survivor);

		} else {
			throw new IdNotFoundException("Survivor with respective code: '" + survivorCode + "' not found.");
		}
	}

}
