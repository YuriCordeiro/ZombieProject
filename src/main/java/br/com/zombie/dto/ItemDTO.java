package br.com.zombie.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "T_ITEM")
@SequenceGenerator(name = "seqItem", sequenceName = "SQ_ITEM", allocationSize = 1)
public class ItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ITM")
	@GeneratedValue(generator = "seqItem", strategy = GenerationType.SEQUENCE)
	private Integer itemCode;

	@Column(name = "DS_ITM")
	private String itemDescription;

	@Column(name = "ITM_AMT")
	private Integer itemAmount;

	@Column(name = "ITM_PRC")
	private Integer itemPrice;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_INV", referencedColumnName="ID_INV", nullable = false) //its necessary to have an inventory to save items.
	private InventoryDTO inventory;

	
	// /******************************************************************************
	// * 					Constructor(s) default - Java Beans
	// ******************************************************************************/
	// public ItemTO() {
	// super();
	// }
	//
	// public ItemTO(String itemDescription, Integer itemAmount, Integer
	// itemPrice) {
	// super();
	// this.itemDescription = itemDescription;
	// this.itemAmount = itemAmount;
	// this.itemPrice = itemPrice;
	// }
	
	/******************************************************************************
	 * 							Getter(s) & Setter(s)
	 ******************************************************************************/
	public Integer getItemCode() {
		return itemCode;
	}

	public void setItemCode(Integer itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public Integer getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(Integer itemAmount) {
		this.itemAmount = itemAmount;
	}

	public Integer getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Integer itemPrice) {
		this.itemPrice = itemPrice;
	}

	public InventoryDTO getInventory() {
		return inventory;
	}

	public void setInventory(InventoryDTO inventory) {
		this.inventory = inventory;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ItemDTO [itemCode=").append(itemCode).append(", itemDescription=").append(itemDescription)
				.append(", itemAmount=").append(itemAmount).append(", itemPrice=").append(itemPrice)
				.append(", inventory=").append(inventory).append("]");
		return builder.toString();
	}

}
