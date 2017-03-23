
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Customer;
import domain.Status;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private CustomerService			customerService;


	// Constructors -----------------------------------------------------------

	public ApplicationService() {
		super();
	}

	//Creates -----------------------------------------------------------------

	public Application create() {
		Application res;
		res = new Application();
		return res;
	}

	//Simple CRUD -------------------------------------------------------------

	public Collection<Application> findAll() {
		Collection<Application> res;
		res = this.applicationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Application findOne(final int applicationId) {
		Application res;
		res = this.applicationRepository.findOne(applicationId);
		Assert.notNull(res);
		return res;
	}

	public Application save(final Application a) {
		Assert.notNull(a);
		return this.applicationRepository.save(a);

	}

	public void delete(final Application a) {
		Assert.notNull(a);
		this.applicationRepository.delete(a);
	}

	//Other business methods --------------------------------------

	public Collection<Application> findCustomerApplications(final Customer customer) {
		Assert.notNull(customer);

		Collection<Application> res;

		res = this.applicationRepository.findCustomerApplications(customer.getId());

		return res;
	}

	public Collection<Application> findCustomerRequestOfferApplications(final Customer customer) {
		Assert.notNull(customer);

		Collection<Application> res;

		res = this.applicationRepository.findCustomerRequestOfferApplications(customer.getId());

		return res;
	}

	public void accept(final Application application) {
		Assert.notNull(application);

		Customer customer;

		customer = this.customerService.findByPrincipal();

		Assert.isTrue(application.getRequestOffer().getCustomer().getId() == customer.getId());
		Assert.isTrue(application.getStatus().equals(Status.PENDING));

		application.setStatus(Status.ACCEPTED);

		this.applicationRepository.save(application);

	}

	public void deny(final Application application) {
		Assert.notNull(application);

		Customer customer;

		customer = this.customerService.findByPrincipal();

		Assert.isTrue(application.getRequestOffer().getCustomer().getId() == customer.getId());
		Assert.isTrue(application.getStatus().equals(Status.PENDING));

		application.setStatus(Status.DENIED);

		this.applicationRepository.save(application);

	}
}
