
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentableEntityRepository;
import domain.CommentableEntity;

@Service
@Transactional
public class CommentableEntityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentableEntityRepository	commentableEntityRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CommentableEntityService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<CommentableEntity> findAll() {
		Collection<CommentableEntity> result;
		result = this.commentableEntityRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public CommentableEntity findOne(final int commentableEntityId) {
		CommentableEntity result;
		result = this.commentableEntityRepository.findOne(commentableEntityId);
		Assert.notNull(result);
		return result;
	}

	public CommentableEntity save(final CommentableEntity commentableEntity) {
		Assert.notNull(commentableEntity);
		CommentableEntity result;
		result = this.commentableEntityRepository.save(commentableEntity);
		return result;
	}

	public void delete(final CommentableEntity commentableEntity) {
		Assert.notNull(commentableEntity);
		Assert.isTrue(commentableEntity.getId() != 0);

		this.commentableEntityRepository.delete(commentableEntity);
	}
	
	public CommentableEntity findOneAlternativo(int id){
		return this.commentableEntityRepository.findOneAlternativo(id);
		
	}

}
