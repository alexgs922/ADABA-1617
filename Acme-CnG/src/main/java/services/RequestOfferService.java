
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestOfferRepository;
import domain.Application;
import domain.Customer;
import domain.RequestOffer;
import domain.Status;

@Service
@Transactional
public class RequestOfferService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RequestOfferRepository	requestOfferRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CustomerService			customerService;


	// Constructors -----------------------------------------------------------

	public RequestOfferService() {
		super();
	}

	//Simple CRUD methods ---------------------------------

	//TODO falta hacer el create

	public Collection<RequestOffer> findAll() {
		Collection<RequestOffer> res;
		res = this.requestOfferRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public RequestOffer findOne(final int requestOfferId) {
		RequestOffer res;
		res = this.requestOfferRepository.findOne(requestOfferId);
		Assert.notNull(res);
		return res;
	}

	public RequestOffer save(final RequestOffer ro) {
		Assert.notNull(ro);
		return this.requestOfferRepository.save(ro);

	}

	public void delete(final RequestOffer ro) {
		Assert.notNull(ro);
		this.requestOfferRepository.delete(ro);
	}

	//Other business methods ---------------------------------------------------

	public Collection<RequestOffer> findAllRequest() {
		Collection<RequestOffer> requests;

		requests = this.requestOfferRepository.findAllRequest();

		return requests;
	}

	public Collection<RequestOffer> findAllOffers() {
		Collection<RequestOffer> offers;

		offers = this.requestOfferRepository.findAllOffers();

		return offers;
	}

	public void applyRequestOffer(final RequestOffer requestOffer) {
		Assert.notNull(requestOffer);

		RequestOffer reqOff;
		Application application;
		Customer customer;

		reqOff = this.requestOfferRepository.findOne(requestOffer.getId());
		application = this.applicationService.create();
		customer = this.customerService.findByPrincipal();

		application.setStatus(Status.PENDING);
		application.setCustomer(customer);
		application.setRequestOffer(reqOff);

		customer.getApplications().add(application);

		this.customerService.save(customer);
		this.applicationService.save(application);

	}
}
