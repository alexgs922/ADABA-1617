
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Place {

	//Constructor -------------------------------------------------------

	public Place() {
		super();
	}


	//Attributes ----------------------------------------------------------

	private String	address;
	private Double	length;
	private Double	latitude;


	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String adress) {
		this.address = adress;
	}

	public Double getLength() {
		return this.length;
	}

	public void setLength(final Double length) {
		this.length = length;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

}
