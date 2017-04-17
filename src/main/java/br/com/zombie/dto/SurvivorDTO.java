package br.com.zombie.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "T_SURVIVOR")
@SequenceGenerator(name = "seqSurvivor", sequenceName = "SQ_SURVIVOR", allocationSize = 1)
public class SurvivorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_SRV")
	@GeneratedValue(generator = "seqSurvivor", strategy = GenerationType.SEQUENCE)
	private Integer survivorCode;

	@Column(name = "NAM_SRV", nullable = false, length = 70)
	private String survivorName;

	@OneToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_GND", referencedColumnName = "ID_GND")
	private GenderDTO survivorGender;

	@Column(name = "SRV_AGE", length = 3)
	private Integer survivorAge;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_LCL", referencedColumnName="ID_LCL", nullable = false)
	private LocalDTO local;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "ID_INV", referencedColumnName="ID_INV", nullable=false) //Auto-increased
	private InventoryDTO survivorInventory;

	// If equals 1 = Infected
	// If equals 0 = Non-Infected
	@Column(name = "IS_INFC")
	private boolean infected;

	// If other survivors warns it >= 3, the flag 'infected' will be true
	@Column(name = "AMT_INF_WRN")
	private Integer amountOfInfectedWarnings;
	
	@Column(name = "AMT_PNTS")
	private Integer points;

	// /******************************************************************************
	// * Constructor(s) default - Java Beans
	// ******************************************************************************/
	// public SurvivorTO() {
	// super();
	// }
	//
	// public SurvivorTO(String survivorName, GenderDTO survivorGender, Integer
	// survivorAge, LocalTO survivorLastLocal, boolean infected) {
	// super();
	// this.survivorName = survivorName;
	// this.survivorGender = survivorGender;
	// this.survivorAge = survivorAge;
	// this.lastSurvivorLocal = survivorLastLocal;
	// this.infected = infected;
	// this.amountOfInfectedWarnings = 0;
	// }

	/******************************************************************************
	 * Getter(s) & Setter(s)
	 ******************************************************************************/
	public Integer getSurvivorCode() {
		return survivorCode;
	}

	public void setSurvivorCode(Integer survivorCode) {
		this.survivorCode = survivorCode;
	}

	public String getSurvivorName() {
		return survivorName;
	}

	public void setSurvivorName(String survivorName) {
		this.survivorName = survivorName;
	}

	public GenderDTO getSurvivorGender() {
		return survivorGender;
	}

	public void setSurvivorGender(GenderDTO survivorGender) {
		this.survivorGender = survivorGender;
	}

	public Integer getSurvivorAge() {
		return survivorAge;
	}

	public void setSurvivorAge(Integer survivorAge) {
		this.survivorAge = survivorAge;
	}

	public InventoryDTO getSurvivorInventory() {
		return survivorInventory;
	}

	public void setSurvivorInventory(InventoryDTO survivorInventory) {
		this.survivorInventory = survivorInventory;
	}

	public boolean isInfected() {
		return infected;
	}

	public void setInfected(boolean infected) {
		this.infected = infected;
	}

	public Integer getAmountOfInfectedWarnings() {
		return amountOfInfectedWarnings;
	}

	public void setAmountOfInfectedWarnings(Integer amountOfInfectedWarnings) {
		this.amountOfInfectedWarnings = amountOfInfectedWarnings;
	}

	public LocalDTO getLocal() {
		return local;
	}

	public void setLocal(LocalDTO local) {
		this.local = local;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
