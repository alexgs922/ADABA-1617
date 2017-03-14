
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	//Constructor ----------------------------------------------------

	public Application() {
		super();
	}


	// Attributes -----------------------------------------------------------

	private Status	status;


	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}


	//Relationships ------------------------------------------------------------------

	private Customer		customer;
	private RequestOffer	requestOffer;


	@Valid
	@ManyToOne(optional = false)
	public RequestOffer getRequestOffer() {
		return this.requestOffer;
	}

	public void setRequestOffer(final RequestOffer requestOffer) {
		this.requestOffer = requestOffer;
	}

	@Valid
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

}
