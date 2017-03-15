
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

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

	@Range(min = -180, max = 180)
	public Double getLength() {
		return this.length;
	}

	public void setLength(final Double length) {
		this.length = length;
	}

	@Range(min = -90, max = 90)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(final Double latitude) {
		this.latitude = latitude;
	}

}
