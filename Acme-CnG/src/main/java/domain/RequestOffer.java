
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class RequestOffer extends CommentableEntity {

	//Constructor --------------------------------------------------------

	public RequestOffer() {
		super();
	}


	//Attributes ---------------------------------------------------------

	private String			title;
	private String			description;
	private Date			moment;
	private Place			originPlace;
	private Place			destinationPlace;
	private RequestOrOffer	requestOrOffer;
	private boolean			banned;


	public RequestOrOffer isRequestOrOffer() {
		return this.requestOrOffer;
	}

	public void setRequestOrOffer(final RequestOrOffer requestOrOffer) {
		this.requestOrOffer = requestOrOffer;
	}

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

	public void setDescription(final String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "originAddress")), @AttributeOverride(name = "length", column = @Column(name = "originLength")), @AttributeOverride(name = "latitude", column = @Column(name = "originLatitude"))
	})
	public Place getOriginPlace() {
		return this.originPlace;
	}

	public void setOriginPlace(final Place originPlace) {
		this.originPlace = originPlace;
	}

	@AttributeOverrides({
		@AttributeOverride(name = "address", column = @Column(name = "destinationAddress")), @AttributeOverride(name = "length", column = @Column(name = "destinationLength")),
		@AttributeOverride(name = "latitude", column = @Column(name = "destinationLatitude"))
	})
	public Place getDestinationPlace() {
		return this.destinationPlace;
	}

	public void setDestinationPlace(final Place destinationPlace) {
		this.destinationPlace = destinationPlace;
	}

	public boolean isBanned() {
		return this.banned;
	}

	public void setBanned(final boolean banned) {
		this.banned = banned;
	}

	public RequestOrOffer getRequestOrOffer() {
		return this.requestOrOffer;
	}


	//Relationships ------------------------------------------------------------------------------------------------------------------

	private Customer	customer;


	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

}
