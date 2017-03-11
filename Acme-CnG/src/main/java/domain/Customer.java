
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	//Constructor -----------------------------------------

	public Customer() {
		super();
	}


	//Relationships -----------------------------------------------------------------

	private Collection<Request>	request;
	private Collection<Offer>	offers;


	@Valid
	@OneToMany()
	public Collection<Offer> getOffers() {
		return this.offers;
	}

	public void setOffers(final Collection<Offer> offers) {
		this.offers = offers;
	}

	@Valid
	@OneToMany()
	public Collection<Request> getRequest() {
		return this.request;
	}

	public void setRequest(final Collection<Request> request) {
		this.request = request;
	}

}
