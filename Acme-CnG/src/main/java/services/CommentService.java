
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.CommentableEntity;

@Service
@Transactional
public class CommentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CommentRepository			commentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CommentableEntityService	commentableEntityService;


	// Constructors -----------------------------------------------------------

	public CommentService() {
		super();
	}

	//Simple CRUD methods ---------------------------------

	public Comment create(int commentableEntity) {
		Actor sender = this.actorService.findByPrincipal();
		Comment result;
		CommentableEntity commentable;
		Date moment;
		moment = new Date(System.currentTimeMillis()-10);
		result = new Comment();
	
		commentable = this.commentableEntityService.findOne(commentableEntity);
		
		result.setMoment(moment);
		result.setActor(sender);
		result.setCommentableEntity(commentable);
	
		return result;


	}
	
	public Collection<Comment> findAll() {
		Collection<Comment> res;
		res = this.commentRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Comment findOne(final int commentId) {
		Comment res;
		res = this.commentRepository.findOne(commentId);
		Assert.notNull(res);
		return res;
	}

	public Comment save(final Comment c) {
		Assert.notNull(c);
		return this.commentRepository.save(c);

	}

	public void delete(final Comment c) {
		Assert.notNull(c);
		this.commentRepository.delete(c);
	}
/*
	public Collection<Comment> findCommentsByActorId(final int actorId) {
		Collection<Comment> result;
		result = this.commentRepository.findCommentsByActorId(actorId);
		Assert.notNull(result);
		return result;

	}*/
}
