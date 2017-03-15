
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestOfferServiceTest extends AbstractTest {

	//The SUT ---------------------------------------------------------------------------------

	@Autowired
	private RequestOfferService	requestOfferService;


	// DRIVER ---------------------------------------------------------------------------------

	protected void template(final String username, final int id, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			//this.requestOfferService.save(id);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driver() {

		final Object testingData[][] = {
			{
				"customer1", 17, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

}
