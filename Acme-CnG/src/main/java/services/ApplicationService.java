
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ApplicationRepository	applicationRepository;


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
}
