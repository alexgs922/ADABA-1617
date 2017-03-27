
package services;

import java.util.ArrayList;
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

	public Comment create(final int commentableEntity) {
		final Actor sender = this.actorService.findByPrincipal();
		Comment result;
		CommentableEntity commentable;
		Date moment;
		moment = new Date(System.currentTimeMillis() - 10);
		result = new Comment();

		commentable = this.commentableEntityService.findOneAlternativo(commentableEntity);

		result.setMoment(moment);
		result.setActor(sender);
		result.setCommentableEntity(commentable);

		return result;

	}

	public Collection<Comment> findAll() {
		Collection<Comment> res;
		Collection<CommentableEntity> commentableEntities;

		commentableEntities = this.commentableEntityService.findAll();
		res = new ArrayList<Comment>();

		for (final CommentableEntity c : commentableEntities)
			res.addAll(c.getCommentsReceived());

		Assert.notNull(res);
		return res;
	}

	public Comment findOne(final int commentId) {
		Collection<Comment> comments;
		Comment res;

		comments = this.findAll();
		res = new Comment();
		for (final Comment c : comments)
			if (c.getId() == commentId)
				res = c;

		Assert.notNull(res);
		return res;
	}

	public Comment save(final Comment c) {
		final Actor sender = this.actorService.findByPrincipal();
		Assert.notNull(c);

		Assert.isTrue((c.getActor().getId() == sender.getId()));
		return this.commentRepository.save(c);

	}

	public void delete(final Comment c) {
		Assert.notNull(c);
		this.commentRepository.delete(c);
	}

	//Other business methods ----------------------------------------------------------------

	public void banComment(final Comment comment) {
		Assert.notNull(comment);

		final Actor principal = this.actorService.findByPrincipal();

		//		Assert.isTrue(principal.getUserAccount().getAuthorities().contains(Authority.ADMIN));

		Assert.isTrue(comment.isBanned() == false);

		comment.setBanned(true);

		this.commentRepository.save(comment);

	}
	/*
	 * public Collection<Comment> findCommentsByActorId(final int actorId) {
	 * Collection<Comment> result;
	 * result = this.commentRepository.findCommentsByActorId(actorId);
	 * Assert.notNull(result);
	 * return result;
	 * 
	 * }
	 */

	public Double findAverageNumberCommentsPerActor() {
		final Double res = this.commentRepository.findAverageNumberCommentsPerActor();
		return res;
	}

	public Double findAverageNumberCommentsPerOffer() {
		final Double res = this.commentRepository.findAverageNumberCommentsPerOffer();
		return res;
	}

	public Double findAverageNumberCommentsPerRequest() {
		final Double res = this.commentRepository.findAverageNumberCommentsPerRequest();
		return res;
	}

	public Double averageNumberCommentsPostedByCustomer() {
		final Double res = this.commentRepository.averageNumberCommentsPostedByCustomer();
		return res;
	}

	public Double averageNumberCommentsPostedByAdministrator() {
		final Double res = this.commentRepository.averageNumberCommentsPostedByAdministrator();
		;
		return res;
	}
}
