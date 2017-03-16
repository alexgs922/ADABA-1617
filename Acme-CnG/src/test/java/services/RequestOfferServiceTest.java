
package services;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.RequestOffer;
import domain.RequestOrOffer;
import forms.RequestOfferForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestOfferServiceTest extends AbstractTest {

	//The SUT ---------------------------------------------------------------------------------

	@Autowired
	private RequestOfferService	requestOfferService;

	@Autowired
	private CustomerService		customerService;


	// DRIVER ---------------------------------------------------------------------------------

	//	protected void template(final String username, final int id, final Class<?> expected) {
	//
	//		Class<?> caught;
	//
	//		caught = null;
	//		try {
	//
	//			this.authenticate(username);
	//
	//			//this.requestOfferService.save(id);
	//
	//			this.unauthenticate();
	//
	//		} catch (final Throwable oops) {
	//			caught = oops.getClass();
	//		}
	//
	//		this.checkExceptions(expected, caught);
	//
	//	}
	//
	//	@Test
	//	public void driver() {
	//
	//		final Object testingData[][] = {
	//			{
	//				"customer1", 17, null
	//			}
	//		};
	//
	//		for (int i = 0; i < testingData.length; i++)
	//			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	//
	//	}

	@Test
	public void testReconstructAndCreate() {

		this.authenticate("customer1");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final String title = "title test1";
		final String description = "description test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2018, 02, 14, 20, 15);
		final Date date = cal.getTime(); // get back a Date object
		final Date moment = date;

		final RequestOrOffer requestOrOffer = RequestOrOffer.OFFER;
		final String originaddress = "Origin address";
		final Double originlength = 15.95;
		final Double originlatitude = 22.14;
		final String destinationaddress = "Destination address";
		final Double destinationlength = 45.89;
		final Double destinationlatitude = -79.25;

		form.setTitle(title);
		form.setDescription(description);
		form.setMoment(moment);
		form.setRequestOrOffer(requestOrOffer);
		form.setOriginaddress(originaddress);
		form.setOriginlatitude(originlatitude);
		form.setOriginlength(originlength);
		form.setDestinationaddress(destinationaddress);
		form.setDestinationlatitude(destinationlatitude);
		form.setDestinationlength(destinationlength);

		final int beforeSave = this.requestOfferService.findAll().size();

		res = this.requestOfferService.reconstruct(form);

		res = this.requestOfferService.save(res);

		this.unauthenticate();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

	}

}
