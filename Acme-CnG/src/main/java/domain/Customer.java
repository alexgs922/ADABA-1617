
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

	private Collection<RequestOffer>	requestsOffers;
	private Collection<Application>		applications;


	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<RequestOffer> getRequestsOffers() {
		return this.requestsOffers;
	}

	public void setRequestsOffers(final Collection<RequestOffer> requestsOffers) {
		this.requestsOffers = requestsOffers;
	}

}
