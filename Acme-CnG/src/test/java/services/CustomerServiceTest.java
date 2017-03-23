
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Customer;
import forms.RegistrationForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	CustomerService customerService;

	

	
	protected void template(final String username, final String password,
			final String passwordCheck,final String phone,final boolean termsOfUse,
			final String name,final String email,final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {

			this.customerService.create();
			RegistrationForm reg = new RegistrationForm();
			
			reg.setUsername(username);
			reg.setPassword(password);
			reg.setPasswordCheck(passwordCheck);
			reg.setPhone(phone);
			reg.setTermsOfUse(termsOfUse);
			reg.setFullName(name);
			reg.setEmail(email);
			Customer customer = this.customerService.reconstruct(reg);
			this.customerService.save(customer);
			this.customerService.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
	@Test
	public void driver() {

		final Object testingData[][] = {
			//Crear un usuario valido
			{"customerPrueba", "customerPrueba1","customerPrueba1","+954670441",true,"customerPrueba","customerPrueba@gmail.com", null}, 
			//Crear un usuario con un username que ya existe en el sistema
			{"customer1", "customerPrueba1","customerPrueba1","+954670441",true,"customerPrueba","customerPrueba@gmail.com", DataIntegrityViolationException.class}, 
			//Crear un usuario con contraseña que no coincida
			{"customerPrueba", "contraseña1","añldskfj","+954670441",true,"customerPrueba","customerPrueba@gmail.com", DataIntegrityViolationException.class}, 
			//Crear un usuario que tiene campos en blanco
			{" ", " ","customerPrueba1","+954670441",true," ","customerPrueba@gmail.com", DataIntegrityViolationException.class},
			//Crear un usuario que tiene un telefono no valido
			{"customerPrueba", "customerPrueba1","customerPrueba1","12345",true,"customerPrueba","customerPrueba@gmail.com", DataIntegrityViolationException.class},
			//Crear un usuario que tiene un mail no valido
			{"customerPrueba", "customerPrueba1","customerPrueba1","+954670441",true,"customerPrueba","customerPrueba", DataIntegrityViolationException.class},
			//Crear un usuario que no ha aceptado las condiciones y terminos de uso
			{"customerPrueba", "customerPrueba1","customerPrueba1","+954670441",false,"customerPrueba","customerPrueba@gmail.com", DataIntegrityViolationException.class} 		
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],
			(String) testingData[i][3],(boolean) testingData[i][4],(String) testingData[i][5], 
			(String) testingData[i][6],(Class<?>) testingData[i][7]);

	}

}
