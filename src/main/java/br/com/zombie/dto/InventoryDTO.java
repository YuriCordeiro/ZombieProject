package br.com.zombie.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="T_INVENTORY")
@SequenceGenerator(name="seqInventory", sequenceName="SQ_INVENTORY", allocationSize=1)
public class InventoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_INV")
	@GeneratedValue(generator="seqInventory", strategy=GenerationType.SEQUENCE)
	private Integer inventoryCode;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "inventory")
	@Column(name="ID_ITM")
	private List<ItemDTO> items = new ArrayList<ItemDTO>();

//	/******************************************************************************
//	 * 					Constructor(s) default - Java Beans
//	 ******************************************************************************/
//	public InventoryTO() {
//		super();
//	}
//
//	public InventoryTO(Integer inventoryCode, List<ItemTO> items) {
//		super();
//		this.inventoryCode = inventoryCode;
//		this.items = items;
//	}

	/******************************************************************************
	 * 							Getter(s) & Setter(s)
	 ******************************************************************************/
	public Integer getInventoryCode() {
		return inventoryCode;
	}

	public void setInventoryCode(Integer inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	public List<ItemDTO> getItems() {
		return items;
	}

	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

}
