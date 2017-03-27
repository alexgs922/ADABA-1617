
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import utilities.AbstractTest;
import domain.Customer;
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

	//AUXILIAR SERVICES  ---------------------------------------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private Validator			validator;


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

	// CASO DE USO 3 : CREACIÓN DE REQUESTS Y OFFERS (POSITIVOS) ---------------------------------------------------------------------------------

	protected void templateCreateRequest(final String username, final String title, final String description, final Date moment, final String originaddress, final Double originlength, final Double originlatitude, final String destinationaddress,
		final Double destinationlength, final Double destinationlatitude, final RequestOrOffer rOrO, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			int beforeSave;
			beforeSave = this.requestOfferService.findAll().size();

			final RequestOfferForm form = new RequestOfferForm();
			RequestOffer res;

			form.setTitle(title);
			form.setDescription(description);
			form.setMoment(moment);
			form.setRequestOrOffer(rOrO);
			form.setOriginaddress(originaddress);
			form.setOriginlatitude(originlatitude);
			form.setOriginlength(originlength);
			form.setDestinationaddress(destinationaddress);
			form.setDestinationlatitude(destinationlatitude);
			form.setDestinationlength(destinationlength);

			res = this.requestOfferService.reconstruct(form);
			res = this.requestOfferService.save(res);
			this.requestOfferService.flush();

			int afterSave;
			afterSave = this.requestOfferService.findAll().size();

			Assert.isTrue(beforeSave < afterSave);
			Assert.isTrue(beforeSave == afterSave - 1);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverCreateRequest() {

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2018, 02, 14, 20, 15);
		final Date date = cal.getTime(); // get back a Date object
		final Date moment1 = date;

		//		final Calendar cal2 = Calendar.getInstance();
		//		cal2.setTimeInMillis(0);
		//		cal2.set(2015, 01, 05, 15, 30);
		//		final Date date2 = cal.getTime();
		//		final Date moment2 = date2;

		final Object testingData[][] = {

			{
				"customer1", "tituloTest1", "descripciónTest1", moment1, "originAddressTest1", 15.95, 22.14, "destinationAddressTest1", 45.89, -79.25, RequestOrOffer.REQUEST, null
			}, {
				"customer1", "tituloTest1.1", "descripciónTest1.1", moment1, "originAddressTest1.1", null, null, "destinationAddressTest1.1", null, null, RequestOrOffer.REQUEST, null
			}, {
				"customer1", "tituloTest1", "descripciónTest1", moment1, "originAddressTest1", 15.95, 22.14, "destinationAddressTest1", 45.89, -79.25, RequestOrOffer.OFFER, null
			}, {
				"customer1", "tituloTest1.1", "descripciónTest1.1", moment1, "originAddressTest1.1", null, null, "destinationAddressTest1.1", null, null, RequestOrOffer.OFFER, null
			}
		//			, {
		//				"customer2", "tituloTest2", "descripcionTest2", null, "originAddressTest2", 15.95, 22.14, "destinationAddressTest2", 45.89, -79.25, ConstraintViolationException.class
		//			}, {
		//				"customer3", "tituloTest3", "", moment1, "originAddressTest3", 15.95, 22.14, "destinationAddressTest3", 45.89, -79.25, ConstraintViolationException.class
		//			}, {
		//				"customer1", "", "descripciónTest4", moment1, "originAddressTest4", 15.95, 22.14, "destinationAddressTest4", 45.89, -79.25, ConstraintViolationException.class
		//			}
		//, {
		//				"customer2", "tituloTest4", "descripciónTest4", moment2, "originAddressTest4", 15.95, 22.14, "destinationAddressTest4", 45.89, -79.25, ConstraintViolationException.class
		//			}, {
		//				"customer3", "tituloTest5", "descripciónTest5", moment1, "", null, null, "", null, null, ConstraintViolationException.class
		//			}, {
		//				"customer3", "tituloTest5", "descripciónTest5", moment1, "", -459874656, 78541259635214785.0, "", 7896553, "/*-*/--/-/fg", ConstraintViolationException.class
		//			}, {
		//				null, "tituloTestNotLoged", "descripciónTestNotLoged", moment1, "originAddressTestNotLoged", 15.95, 22.14, "destinationAddressTestNotLoged", 45.89, -79.25, IllegalArgumentException.class
		//			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateRequest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Date) testingData[i][3], (String) testingData[i][4], (Double) testingData[i][5], (Double) testingData[i][6],
				(String) testingData[i][7], (Double) testingData[i][8], (Double) testingData[i][9], (RequestOrOffer) testingData[i][10], (Class<?>) testingData[i][11]);

	}

	// CASO DE USO 4 : CREACIÓN DE REQUESTS Y OFFERS (NEGATIVOS)  ---------------------------------------------------------------------------------

	// Comprobando que no se creen requests con título vacío
	@Test(expected = ConstraintViolationException.class)
	public void testCreateRequestWithValidationErrors1() {

		this.authenticate("customer2");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "";
		final String description = "description test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2018, 02, 14, 20, 15);
		final Date date = cal.getTime();
		final Date moment = date;

		final RequestOrOffer requestOrOffer = RequestOrOffer.REQUEST;
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

		res = this.requestOfferService.reconstruct(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	// Comprobando que no se creen offers con título vacío
	@Test(expected = ConstraintViolationException.class)
	public void testCreateOfferWithValidationErrors1() {

		this.authenticate("customer3");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "";
		final String description = "description test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2018, 02, 14, 20, 15);
		final Date date = cal.getTime();
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

		res = this.requestOfferService.reconstruct(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	// Comprobando que no se creen requests con descripción vacia
	@Test(expected = ConstraintViolationException.class)
	public void testCreateRequestWithValidationErrors2() {

		this.authenticate("customer1");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2018, 02, 14, 20, 15);
		final Date date = cal.getTime();
		final Date moment = date;

		final RequestOrOffer requestOrOffer = RequestOrOffer.REQUEST;
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

		res = this.requestOfferService.reconstruct(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	// Comprobando que no se creen  offers con descripción vacia
	@Test(expected = ConstraintViolationException.class)
	public void testCreateOfferWithValidationErrors2() {

		this.authenticate("customer2");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2018, 02, 14, 20, 15);
		final Date date = cal.getTime();
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

		res = this.requestOfferService.reconstruct(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	//Comprobando que no se creen requests sin momento
	@Test(expected = ConstraintViolationException.class)
	public void testCreateRequestWithValidationErrors3() {

		this.authenticate("customer3");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "description test";

		final RequestOrOffer requestOrOffer = RequestOrOffer.REQUEST;
		final String originaddress = "Origin address";
		final Double originlength = 15.95;
		final Double originlatitude = 22.14;
		final String destinationaddress = "Destination address";
		final Double destinationlength = 45.89;
		final Double destinationlatitude = -79.25;

		form.setTitle(title);
		form.setDescription(description);
		form.setRequestOrOffer(requestOrOffer);
		form.setOriginaddress(originaddress);
		form.setOriginlatitude(originlatitude);
		form.setOriginlength(originlength);
		form.setDestinationaddress(destinationaddress);
		form.setDestinationlatitude(destinationlatitude);
		form.setDestinationlength(destinationlength);

		res = this.requestOfferService.reconstruct(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	//Comprobando que no se creen Offers sin momento
	@Test(expected = ConstraintViolationException.class)
	public void testCreateOfferWithValidationErrors3() {

		this.authenticate("customer1");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "description test";

		final RequestOrOffer requestOrOffer = RequestOrOffer.OFFER;
		final String originaddress = "Origin address";
		final Double originlength = 15.95;
		final Double originlatitude = 22.14;
		final String destinationaddress = "Destination address";
		final Double destinationlength = 45.89;
		final Double destinationlatitude = -79.25;

		form.setTitle(title);
		form.setDescription(description);
		form.setRequestOrOffer(requestOrOffer);
		form.setOriginaddress(originaddress);
		form.setOriginlatitude(originlatitude);
		form.setOriginlength(originlength);
		form.setDestinationaddress(destinationaddress);
		form.setDestinationlatitude(destinationlatitude);
		form.setDestinationlength(destinationlength);

		res = this.requestOfferService.reconstruct(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	//Comprobando que no se creem requests con momento en el pasado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestWithValidationErrors4() {

		this.authenticate("customer1");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "descripción test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2001, 02, 14, 20, 15);
		final Date date = cal.getTime();
		final Date moment = date;

		final RequestOrOffer requestOrOffer = RequestOrOffer.REQUEST;
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

		res = this.requestOfferService.reconstructForTests(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	//Comprobando que no se creen Offers con momento en el pasado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateOfferWithValidationErrors4() {

		this.authenticate("customer1");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "descripción test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2001, 02, 14, 20, 15);
		final Date date = cal.getTime();
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

		res = this.requestOfferService.reconstructForTests(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	//Comprobando que no se creen RequestsOffers si no hay customer autenticado en el sistema
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestOfferWithValidationErrors5() {

		this.unauthenticate();

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "descripción test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2019, 02, 14, 20, 15);
		final Date date = cal.getTime();
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

		res = this.requestOfferService.reconstruct(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

	}

	//Comprobando que no se creen requestsOffers con un origin place o destination place vacios
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestOfferWithValidationErrors6() {

		this.authenticate("customer1");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "descripción test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2020, 02, 14, 20, 15);
		final Date date = cal.getTime();
		final Date moment = date;

		final RequestOrOffer requestOrOffer = RequestOrOffer.REQUEST;
		final String originaddress = "";
		final Double originlength = 15.95;
		final Double originlatitude = 22.14;
		final String destinationaddress = "";
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

		res = this.requestOfferService.reconstructForTests(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	//Comprobando que no se creen requestsOffers con valores de latitud y longitud no válidos
	@Test(expected = IllegalArgumentException.class)
	public void testCreateRequestOfferWithValidationErrors7() {

		this.authenticate("customer2");

		final RequestOfferForm form = new RequestOfferForm();
		RequestOffer res;

		final int beforeSave = this.requestOfferService.findAll().size();

		final String title = "titulo test";
		final String description = "descripción test";

		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(2020, 02, 14, 20, 15);
		final Date date = cal.getTime();
		final Date moment = date;

		final RequestOrOffer requestOrOffer = RequestOrOffer.REQUEST;
		final String originaddress = "origin address";
		final Double originlength = 1578965215785789562.95;
		final Double originlatitude = 22.14;
		final String destinationaddress = "destination address";
		final Double destinationlength = 45.89;
		final Double destinationlatitude = -789566278959679.25;

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

		res = this.requestOfferService.reconstructForTests(form);
		res = this.requestOfferService.save(res);
		this.requestOfferService.flush();

		final int afterSave = this.requestOfferService.findAll().size();

		Assert.isTrue(beforeSave < afterSave);
		Assert.isTrue(beforeSave == afterSave - 1);

		this.unauthenticate();

	}

	// CASO DE USO 5 : SOLICITAR UNA REQUEST/OFFER  ---------------------------------------------------------------------------------

	protected void templateApplyRequestOffer(final String username, final RequestOffer requestOffer, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			this.requestOfferService.applyRequestOffer(requestOffer);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverApplyRequestOffer() {

		final RequestOffer request1 = this.requestOfferService.findOne(117); // Obtenemos la request con id = 117 
		final RequestOffer request2 = this.requestOfferService.findOne(118); // Obtenemos la request con id = 118 
		final RequestOffer offer1 = this.requestOfferService.findOne(113); //Obtenemos la offer con id = 113
		final RequestOffer offer4 = this.requestOfferService.findOne(116); //Obtenemos la offer con id = 116

		final Object testingData[][] = {
			//TEST POSITIVO: Solicitar una request correctamente. Se solicita una request que no es del customer1.
			{
				"customer1", request2, null
			},
			//TEST POSITIVO: Solicitar una offer correctamente. Se solicita una request que no es del customer2
			{
				"customer2", offer1, null
			},
			//TEST NEGATIVO: Solicitar una request que pertenece al customer que la creó.
			{
				"customer2", request1, IllegalArgumentException.class
			},
			//TEST NEGATIVO: Solicitar una request que pertenece al customer que la creó.
			{
				"customer2", offer4, IllegalArgumentException.class
			},
			//TEST NEGATIVO: Solicitar una request que ya esta solicitada
			{
				"customer1", request1, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateApplyRequestOffer((String) testingData[i][0], (RequestOffer) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	// CASO DE USO 6 : BANNEAR UNA REQUEST/OFFER  ---------------------------------------------------------------------------------

	protected void templateBanRequestOffer(final String username, final RequestOffer requestOffer, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			this.requestOfferService.banRequestOffer(requestOffer);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	@Test
	public void driverBanRequestOffer() {

		final RequestOffer request3 = this.requestOfferService.findOne(119); // Obtenemos la request con id = 119
		final RequestOffer request2 = this.requestOfferService.findOne(118); // Obtenemos la request con id = 118 
		final RequestOffer offer1 = this.requestOfferService.findOne(113); //Obtenemos la offer con id = 113
		final RequestOffer offer3 = this.requestOfferService.findOne(115); //Obtenemos la offer con id = 115

		final Object testingData[][] = {
			//TEST POSITIVO: Bannear un request correctamente. Se bannea un request que no esta banneada aun.
			{
				"admin", request2, null
			},
			//TEST POSITIVO: Bannear un offer correctamente. Se bannea un offer que no esta banneada aun.
			{
				"admin", offer1, null
			},
			//TEST NEGATIVO: Bannear una request que ya esta banneada.
			{
				"admin", request3, IllegalArgumentException.class
			},
			//TEST NEGATIVO: Bannear una offer que ya esta banneada.
			{
				"admin", offer3, IllegalArgumentException.class
			},
			//TEST NEGATIVO: Bannear una offer teniendo en el sistema el rol de customer.
			{
				"customer1", offer3, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateBanRequestOffer((String) testingData[i][0], (RequestOffer) testingData[i][1], (Class<?>) testingData[i][2]);

	}

}
