package br.com.zombie.dto;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ManagedBean
@Entity
@Table(name="T_LOCAL")
public class LocalDTO {

	@Id
	@Column(name="ID_LCL")
	private Integer localCode;

	@Column(name="DS_LAT")
	private String latitude = "00.000";
	
	@Column(name="DS_LNG")
	private String longitude = "000.000";
	
	
//	/******************************************************************************
//	 * 					Constructor(s) default - Java Beans
//	 ******************************************************************************/
//	public LocalTO() {
//		super();
//	}
//
//	public LocalTO(Integer localCode, String xCoordinate, String yCoordinate) {
//		super();
//		this.localCode = localCode;
//		this.xCoordinate = xCoordinate;
//		this.yCoordinate = yCoordinate;
//	}


	/******************************************************************************
	 * 							Getter(s) & Setter(s)
	 ******************************************************************************/

	public Integer getLocalCode() {
		return localCode;
	}

	public void setLocalCode(Integer localCode) {
		this.localCode = localCode;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public Serializable getKey() {
		return localCode;
	}
	
}
