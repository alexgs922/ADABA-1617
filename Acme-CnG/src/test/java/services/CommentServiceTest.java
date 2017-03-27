
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private CommentService	commentService;

	
	//USE CASE 1: 
	protected void templateBanComment(final String username, final Comment comment, final Class<?> expected) {

		Class<?> caught;

		caught = null;
		try {
			this.authenticate(username);

			this.commentService.banComment(comment);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}

	//USE CASE 2: 
		protected void templateCreateAComment(final String username, final int commentableEntity, final Class<?> expected) {
			
			Class<?> caught;

			caught = null;
			try {
				this.authenticate(username);

				Comment c = this.commentService.create(commentableEntity);
				this.commentService.save(c);
				this.unauthenticate();
				
			} catch (final Throwable oops) {
				caught = oops.getClass();
			}

			this.checkExceptions(expected, caught);			
			
		}


	
	@Test
	public void driverBanComment() {

		final Comment comment1 = this.commentService.findOne(102); // Obtenemos de la base de datos el comentario con id = 102
		final Comment comment2 = this.commentService.findOne(103); // Obtenemos de la base de datos el comentario con id = 103

		final Object testingData[][] = {
			//TEST POSITIVO: Bannear un comentario correctamente.
			{
				"admin", comment1, null
			},
			//TEST NEGATIVO: Bannear un commentario que ya estaba banneado.
			{
				"admin", comment2, IllegalArgumentException.class
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateBanComment((String) testingData[i][0], (Comment) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	@Test
	public void driverCreateComment() {

		final Object testingData[][] = {
			//Crear un comentario valido
			{"customer1", 113, null},
			
			//Intentar crear un comentario si no estas logeado
			{null, 113, IllegalArgumentException.class},
		
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAComment((String) testingData[i][0], (int) testingData[i][1],(Class<?>) testingData[i][2]);

	}

	
	
}
