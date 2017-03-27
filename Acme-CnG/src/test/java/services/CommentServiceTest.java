
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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


	//USE CASE 2: 
		protected void templateCreateAComment(final String username, final int commentableEntity,final String text,final String title,final int stars, final Class<?> expected) {
			
			Class<?> caught;

			caught = null;
			try {
				this.authenticate(username);

				Comment c = this.commentService.create(commentableEntity);
				c.setText(text);
				c.setTitle(title);
				c.setStars(stars);
				this.commentService.save(c);
				this.unauthenticate();
				this.commentService.flush();
			} catch (final Throwable oops) {
				caught = oops.getClass();
			}

			this.checkExceptions(expected, caught);

			
			
		}

	
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
			{"customer1", 113, "Texto","Titulo",5,null},
			//Intentar crear un comentario si no estas logeado
			{null, 113,"Texto","titulo",5, IllegalArgumentException.class},
			//Intentar crear un comentario vacio
			{"customer1", 113,null,null,5, ConstraintViolationException.class},
			//Intentar crear un comentario con un valor negativo de estrellas
			{"customer1", 113,"texto","titulo",-1, ConstraintViolationException.class},
			//Intentar crear un comentario con un valor superior al maximo
			{"customer1", 113,"texto","titulo",6, ConstraintViolationException.class}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCreateAComment((String) testingData[i][0], (int) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3],
					(int) testingData[i][4],(Class<?>) testingData[i][5]);

	}

	
	
}
