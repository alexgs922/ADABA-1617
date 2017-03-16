
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import utilities.validators.FutureDate;
import domain.RequestOrOffer;

@FutureDate
public class RequestOfferForm {

	// Constructors -----------------------------------------------------------

	public RequestOfferForm() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String			title;
	private String			description;
	private Date			moment;
	private RequestOrOffer	requestOrOffer;
	private String			originaddress;
	private Double			originlength;
	private Double			originlatitude;
	private String			destinationaddress;
	private Double			destinationlength;
	private Double			destinationlatitude;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public RequestOrOffer getRequestOrOffer() {
		return this.requestOrOffer;
	}

	public void setRequestOrOffer(final RequestOrOffer requestOrOffer) {
		this.requestOrOffer = requestOrOffer;
	}

	@NotBlank
	public String getOriginaddress() {
		return this.originaddress;
	}

	public void setOriginaddress(final String originaddress) {
		this.originaddress = originaddress;
	}

	@Range(min = -180, max = 180)
	public Double getOriginlength() {
		return this.originlength;
	}

	public void setOriginlength(final Double originlength) {
		this.originlength = originlength;
	}

	@Range(min = -90, max = 90)
	public Double getOriginlatitude() {
		return this.originlatitude;
	}

	public void setOriginlatitude(final Double originlatitude) {
		this.originlatitude = originlatitude;
	}

	@NotBlank
	public String getDestinationaddress() {
		return this.destinationaddress;
	}

	public void setDestinationaddress(final String destinationaddress) {
		this.destinationaddress = destinationaddress;
	}

	@Range(min = -180, max = 180)
	public Double getDestinationlength() {
		return this.destinationlength;
	}

	public void setDestinationlength(final Double destinationlength) {
		this.destinationlength = destinationlength;
	}

	@Range(min = -90, max = 90)
	public Double getDestinationlatitude() {
		return this.destinationlatitude;
	}

	public void setDestinationlatitude(final Double destinationlatitude) {
		this.destinationlatitude = destinationlatitude;
	}

}
