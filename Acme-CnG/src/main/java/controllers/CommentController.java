package controllers;

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
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	// Services ------------------------------------------------

	@Autowired
	private CommentService	commentService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createComment(@RequestParam int commentableEntityId) {
		ModelAndView result;
		Comment comment;
		comment = commentService.create(commentableEntityId);

		result = createEditModelAndView(comment);
		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Comment comment, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(comment);
		} else {
			try {
				commentService.save(comment);
				result = new ModelAndView("redirect:../");
			} catch (Throwable oops) {
				result = createEditModelAndView(comment, "comment.commit.error");
			}
		}
		return result;
	}

	// Ancillary methods ---------------------------------------------------------
	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result;
		result = createEditModelAndView(comment, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(Comment comment, String message) {
		ModelAndView result;
		result = new ModelAndView("comment/create");
		result.addObject("comment", comment);
		result.addObject("message", message);
		return result;

	}
}
