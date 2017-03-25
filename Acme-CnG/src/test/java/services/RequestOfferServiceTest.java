
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;
import domain.RequestOffer;
import domain.RequestOrOffer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RequestOfferServiceTest extends AbstractTest {

	//The SUT ---------------------------------------------------------------------------------

	@Autowired
	private RequestOfferService	requestOfferService;

	//AUXILIAR SERVICES  ---------------------------------------------------------------------------------
	@Autowired
	private CustomerService		customerService;


	// CASO DE USO 1 : LISTADO GENERAL DE REQUEST Y OFFERS ---------------------------------------------------------------------------------

	protected void templateListUseCase1(final String username, final Collection<RequestOffer> res, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			int cont_request = 0;
			int cont_offers = 0;

			for (final RequestOffer ro : res) {

				Assert.isTrue(ro.isBanned() == false);

				if (ro.isRequestOrOffer().equals(RequestOrOffer.REQUEST))
					cont_request += 1;

				else
					cont_offers += 1;

			}

			if (cont_request != 0) {
				Assert.isTrue(res.size() == 2);
				Assert.isTrue(res.size() == cont_request);
			} else {
				Assert.isTrue(res.size() == 3);
				Assert.isTrue(res.size() == cont_offers);
			}

			this.unauthenticate();
			this.requestOfferService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverListUseCase1() {

		final Object testingData[][] = {

			// Para el caso de uso en que los customers pueden ver las listas de Request y Offers que hay en el sistema, probaremos que:

			//1. Se recojan de la base de datos el número correcto total de request y de offers que debería
			//2. No se muestre en el listado ninguna request ni offer banneada

			//En los dos primeros test del caso de uso recogemos datos que deben cumplir el test positivamente y en los dos casos de uso últimos se incumplen
			// las restricciones, pues aparecen en cada colección una request y una offer más, que además están baneadas por el administrador.
			{
				"customer1", this.requestOfferService.findAllRequestNotBanned(), null
			}, {
				"customer2", this.requestOfferService.findAllOffersNotBanned(), null
			}, {
				"customer3", this.requestOfferService.findAllRequest(), IllegalArgumentException.class
			}, {
				"customer1", this.requestOfferService.findAllOffers(), IllegalArgumentException.class
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListUseCase1((String) testingData[i][0], (Collection<RequestOffer>) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	// CASO DE USO 2 : LISTADO PROPIO DE UN CUSTOMER DE SUS REQUEST Y SUS OFFERS (BANEADAS Y NO BANEADAS) ---------------------------------------------------------------------------------

	protected void templateListUseCase2(final String username, final Collection<RequestOffer> request, final Collection<RequestOffer> offers, final Collection<RequestOffer> banned, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Customer c = this.customerService.findCustomerByUsername(username);

			Collection<RequestOffer> requestsAndOffers;
			requestsAndOffers = new ArrayList<RequestOffer>();
			requestsAndOffers.addAll(request);
			requestsAndOffers.addAll(offers);

			for (final RequestOffer re : request)
				Assert.isTrue(re.isRequestOrOffer() == RequestOrOffer.REQUEST);

			for (final RequestOffer of : offers)
				Assert.isTrue(of.isRequestOrOffer() == RequestOrOffer.OFFER);

			for (final RequestOffer ro : requestsAndOffers) {
				Assert.isTrue(ro.getCustomer().getId() == c.getId());
				Assert.isTrue(ro.isBanned() == false);

			}

			for (final RequestOffer ro : banned) {
				Assert.isTrue(ro.getCustomer().getId() == c.getId());
				Assert.isTrue(ro.isBanned() == true);

			}

			if (username == "customer1") {

				Assert.isTrue(request.size() == 0);
				Assert.isTrue(offers.size() == 2);
				Assert.isTrue(banned.size() == 1);

			} else if (username == "customer2") {

				Assert.isTrue(request.size() == 1);
				Assert.isTrue(offers.size() == 1);
				Assert.isTrue(banned.size() == 1);

			} else if (username == "customer3") {

				Assert.isTrue(request.size() == 1);
				Assert.isTrue(offers.size() == 0);
				Assert.isTrue(banned.size() == 0);
			}

			this.unauthenticate();
			this.requestOfferService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverListUseCase2() {

		// Para el caso de uso en que los customers pueden ver sus propias listas de Request y Offers baneadas y sin banear, 
		//y teniendo en cuenta que resaltamos las baneadas mostrándolas en una lista separada de las demás, probaremos que:

		//1. Se recojan de la base de datos el número correcto total de request y de offers sin banear que debería para cada customer
		//2. Se recojan de la base de datos el número correcto total de request y de offers baneadas que debería para cada customer
		//3. Que en el listado de request sólo haya request, que en el de offers sólo haya offers y que en el de request o offers banneadas solo aparezcan las que correspondan
		//4. Que a todos esos listados sólo tiene acceso el autor de las mismas

		final Customer cus1 = this.customerService.findOne(98);
		final Customer cus2 = this.customerService.findOne(99);
		final Customer cus3 = this.customerService.findOne(100);

		final Object testingData[][] = {

			{
				//Test positivo: el customer1 recibe sus request, offers y banned propias
				"customer1", this.requestOfferService.findAllRequestFromPrincipal(cus1), this.requestOfferService.findAllOffersFromPrincipal(cus1), this.requestOfferService.findAllBannedRequestsOffersFromPrincipal(cus1), null
			}, {
				//Test positivo: el customer2 recibe sus request, offers y banned propias
				"customer2", this.requestOfferService.findAllRequestFromPrincipal(cus2), this.requestOfferService.findAllOffersFromPrincipal(cus2), this.requestOfferService.findAllBannedRequestsOffersFromPrincipal(cus2), null
			}, {
				////Test positivo: el customer3 recibe sus request, offers y banned propias
				"customer3", this.requestOfferService.findAllRequestFromPrincipal(cus3), this.requestOfferService.findAllOffersFromPrincipal(cus3), this.requestOfferService.findAllBannedRequestsOffersFromPrincipal(cus3), null
			}, {
				//Test negativo: el customer2 intenta acceder a la información del customer 1
				"customer2", this.requestOfferService.findAllRequestFromPrincipal(cus1), this.requestOfferService.findAllOffersFromPrincipal(cus1), this.requestOfferService.findAllBannedRequestsOffersFromPrincipal(cus1), IllegalArgumentException.class
			}, {
				//Test negativo: el customer2 intenta acceder a las request del customer 1 y a las banned del customer 3
				"customer2", this.requestOfferService.findAllRequestFromPrincipal(cus1), this.requestOfferService.findAllOffersFromPrincipal(cus2), this.requestOfferService.findAllBannedRequestsOffersFromPrincipal(cus3), IllegalArgumentException.class
			}, {
				//Test negativo: el customer 2 intenta mostrar en su lista los listados generales del sistema
				"customer2", this.requestOfferService.findAllRequestNotBanned(), this.requestOfferService.findAllRequest(), this.requestOfferService.findAllOffers(), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateListUseCase2((String) testingData[i][0], (Collection<RequestOffer>) testingData[i][1], (Collection<RequestOffer>) testingData[i][2], (Collection<RequestOffer>) testingData[i][3], (Class<?>) testingData[i][4]);

	}

	// CASO DE USO 3 : CREACIÓN DE REQUESTS  ---------------------------------------------------------------------------------

	protected void templateCreateRequest(final String username, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//	@Test
	//	public void testReconstructAndCreate() {
	//
	//		this.authenticate("customer1");
	//
	//		final RequestOfferForm form = new RequestOfferForm();
	//		RequestOffer res;
	//
	//		final String title = "title test1";
	//		final String description = "description test";
	//
	//		final Calendar cal = Calendar.getInstance();
	//		cal.setTimeInMillis(0);
	//		cal.set(2018, 02, 14, 20, 15);
	//		final Date date = cal.getTime(); // get back a Date object
	//		final Date moment = date;
	//
	//		final RequestOrOffer requestOrOffer = RequestOrOffer.OFFER;
	//		final String originaddress = "Origin address";
	//		final Double originlength = 15.95;
	//		final Double originlatitude = 22.14;
	//		final String destinationaddress = "Destination address";
	//		final Double destinationlength = 45.89;
	//		final Double destinationlatitude = -79.25;
	//
	//		form.setTitle(title);
	//		form.setDescription(description);
	//		form.setMoment(moment);
	//		form.setRequestOrOffer(requestOrOffer);
	//		form.setOriginaddress(originaddress);
	//		form.setOriginlatitude(originlatitude);
	//		form.setOriginlength(originlength);
	//		form.setDestinationaddress(destinationaddress);
	//		form.setDestinationlatitude(destinationlatitude);
	//		form.setDestinationlength(destinationlength);
	//
	//		final int beforeSave = this.requestOfferService.findAll().size();
	//
	//		res = this.requestOfferService.reconstruct(form);
	//
	//		res = this.requestOfferService.save(res);
	//
	//		this.unauthenticate();
	//
	//		final int afterSave = this.requestOfferService.findAll().size();
	//
	//		Assert.isTrue(beforeSave < afterSave);
	//		Assert.isTrue(beforeSave == afterSave - 1);
	//		this.requestOfferService.flush();
	//
	//	}

}
