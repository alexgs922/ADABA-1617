
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.CommentableEntityService;
import domain.Comment;
import domain.CommentableEntity;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services ------------------------------------------------

	@Autowired
	private CommentService				commentService;

	@Autowired
	private CommentableEntityService	commentableEntityService;

	@Autowired
	private ActorService				actorService;


	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listComments(@RequestParam final int commentableEntityId) {
		ModelAndView result;
		Collection<Comment> comments;
		final CommentableEntity c = this.commentableEntityService.findOneAlternativo(commentableEntityId);

		comments = new ArrayList<Comment>();

		for (final Comment co : c.getCommentsReceived())
			if (co.isBanned() == false)
				comments.add(co);

		result = new ModelAndView("comment/listComments");
		result.addObject("comments", comments);
		result.addObject("commentable", c.getId());

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createComment(@RequestParam final int commentableEntityId) {
		ModelAndView result;
		Comment comment;

		comment = this.commentService.create(commentableEntityId);

		result = this.createEditModelAndView(comment);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(comment);
		else
			try {
				this.commentService.save(comment);
				result = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.commit.error");
			}
		return result;
	}

	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Comment comment) {
		ModelAndView result;
		result = this.createEditModelAndView(comment, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		ModelAndView result;
		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("message", message);
		return result;

	}
}
