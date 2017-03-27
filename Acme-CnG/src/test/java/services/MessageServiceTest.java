
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
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

	@Autowired
	private ActorService			actorService;


	//Customers = 98,99,100
	//Admin = 97

	//Crear mensajes sin errores de validacion y otros casos comunes
	protected void template(final String username, final int enviarId, final int recibirId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Actor envia = this.actorService.findOneToSent(enviarId);
			final Actor recibe = this.actorService.findOneToSent(recibirId);

			final Collection<PrivateMessage> enviadosInicial = envia.getSendedMessages();
			final int numeroDeMensajesEnviados = enviadosInicial.size();

			final Collection<PrivateMessage> recibidosInicial = recibe.getRecivedMessages();
			final int numeroDeMensajesRecibidos = recibidosInicial.size();

			final PrivateMessage pm = this.privateMessageService.create();

			pm.setAttachments("");
			pm.setCopy(true);
			pm.setRecipient(recibe);
			pm.setSender(envia);
			pm.setText("Me gustaria asistir mañana dia 15 a las 9:00");
			pm.setTitle("Tutoria");

			this.privateMessageService.save(pm);

			Assert.isTrue(envia.getSendedMessages().size() == numeroDeMensajesEnviados + 1);
			Assert.isTrue(recibe.getRecivedMessages().size() == numeroDeMensajesRecibidos + 1);

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
			{   //customer 1 envia mensaje correcto a customer 2
				"customer1", 98, 99, null
			}, {
				//customer 2 enviar mensaje correcto a customer 1
				"customer2", 99, 98, null
			}, {
				//customer 3 envia mensaje correcto a customer 1
				"customer3", 100, 98, null
			}, {
				//administrador envia mensaje correcto a customer 3
				"admin", 97, 100, null
			}, {
				//Simular post hacking. Logeado como customer3, pero en realidad soy customer 2 intentando enviar mensaje a customer 1
				"customer3", 99, 98, IllegalArgumentException.class
			}, {
				//Usuario no autenticado intenta enviar mensaje a otro actor.
				null, 98, 99, IllegalArgumentException.class
			}, {
				//Customer 1 intenta enviar a un customer o admin que no existe un mensaje
				"customer1", 98, 1000, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	//Crear mensajes con errores de validacion
	protected void template2(final String username, final int enviarId, final int recibirId, final int opcionId, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final Actor envia = this.actorService.findOneToSent(enviarId);
			final Actor recibe = this.actorService.findOneToSent(recibirId);

			final Collection<PrivateMessage> enviadosInicial = envia.getSendedMessages();
			final int numeroDeMensajesEnviados = enviadosInicial.size();

			final Collection<PrivateMessage> recibidosInicial = recibe.getRecivedMessages();
			final int numeroDeMensajesRecibidos = recibidosInicial.size();

			final PrivateMessage pm = this.privateMessageService.create();

			if (opcionId == 1) {
				pm.setAttachments("");
				pm.setCopy(true);
				pm.setRecipient(recibe);
				pm.setSender(envia);
				pm.setTitle("Reunion de mañana");

			}
			if (opcionId == 2) {
				pm.setAttachments("");
				pm.setCopy(true);
				pm.setRecipient(recibe);
				pm.setSender(envia);
				pm.setText("Me gustaria asistir mañana dia 15 a las 9:00");

			}
			if (opcionId == 3) {
				pm.setAttachments("");
				pm.setCopy(true);
				pm.setSender(envia);
				pm.setTitle("Reunion de mañana");
				pm.setText("Me gustaria asistir mañana dia 15 a las 9:00");

			}
			if (opcionId == 4) {
				pm.setAttachments("");
				pm.setCopy(true);
				pm.setRecipient(recibe);
				pm.setTitle("Reunion de mañana");
				pm.setText("Me gustaria asistir mañana dia 15 a las 9:00");

			}
			if (opcionId == 5) {

			}

			this.privateMessageService.save(pm);

			this.unauthenticate();
			this.privateMessageService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
	@Test
	public void driver2() {

		final Object testingData[][] = {
			{
				//customer 2 intenta enviar mensaje sin texto a customer 1
				"customer2", 99, 98, 1, ConstraintViolationException.class
			}, {
				//customer 3 intenta enviar mensaje sin titulo a customer 2
				"customer3", 100, 99, 2, ConstraintViolationException.class
			}, {
				//customer 1 intenta enviar mensaje sin recipient
				"customer1", 98, 99, 3, NullPointerException.class
			}, {
				//No existe sender
				"customer1", 98, 99, 4, NullPointerException.class
			}, {
				//Mensaje vacio
				"customer1", 98, 99, 5, NullPointerException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template2((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);

	}
}
