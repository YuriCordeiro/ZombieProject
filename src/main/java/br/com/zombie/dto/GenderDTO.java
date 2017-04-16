package br.com.zombie.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_GENDER")
@SequenceGenerator(name="seqGender", sequenceName="SQ_GENDER", allocationSize=1)
public class GenderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_GND")
	@GeneratedValue(generator="seqGender", strategy=GenerationType.SEQUENCE)
	private Integer genderId;
	
	@Column(name="DS_GND", nullable=false, length=12)
	private String genderDescription;

	
	public Integer getGenderId() {
		return genderId;
	}

	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
	}

	public String getGenderDescription() {
		return genderDescription;
	}

	public void setGenderDescription(String genderDescription) {
		this.genderDescription = genderDescription;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenderDTO [genderId=").append(genderId).append(", genderDescription=").append(genderDescription)
				.append("]");
		return builder.toString();
	}
	
}
