
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Banner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BannerServiceTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private BannerService	bannerService;


	//Banner = 101
	//Admin = 97

	//USE CASE:
	protected void template(final String username, final Banner banner, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			banner.setUrl("https://c1.staticflickr.com/3/2875/32662160373_4a9e9e2b1f_b.jpg");

			this.bannerService.save(banner);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
	@Test
	public void driver() {

		final Banner banner = this.bannerService.findOne(101);

		final Object testingData[][] = {
			{   //admin edita banner
				"admin", banner, null
			}, {
				//customer intenta editar banner
				"customer2", banner, IllegalArgumentException.class
			}, {
				//Usuario no autenticado intenta editar banner.
				null, banner, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Banner) testingData[i][1], (Class<?>) testingData[i][2]);

	}

}
