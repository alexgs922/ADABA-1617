
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PrivateMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private PrivateMessageService	privateMessageService;


	protected void template(final String username, final int messageId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final PrivateMessage pm = this.privateMessageService.findOne(messageId);

			pm.setText("holaaaaaa");

			this.privateMessageService.save(pm);

			this.unauthenticate();
			this.privateMessageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
	@Test
	public void driver() {

		final Object testingData[][] = {
			{
				"customer1", 106, null
			}, {
				"customer2", 107, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

}
