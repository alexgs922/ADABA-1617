
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CommentService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Comment;

@Controller
@RequestMapping("/comment/administrator")
public class AdministratorCommentController extends AbstractController {

	//Services -------------------------------------------------------------

	@Autowired
	private CommentService			commentService;

	@Autowired
	private AdministratorService	administratorService;


	//Methods --------------------------------------------------------------

	@RequestMapping(value = "/banComment", method = RequestMethod.GET)
	public ModelAndView banRequestOffer(@RequestParam final int commentId) {
		ModelAndView result;
		Comment comment;

		comment = this.commentService.findOne(commentId);

		this.commentService.banComment(comment);

		result = new ModelAndView("redirect:listComments.do");

		return result;

	}

	@RequestMapping(value = "/listComments", method = RequestMethod.GET)
	public ModelAndView listAllComments() {
		ModelAndView result;
		Collection<Comment> comments;
		Administrator administrator;

		comments = this.commentService.findAll();
		administrator = this.administratorService.findByPrincipal();

		result = new ModelAndView("comment/administrator/listComments");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/administrator/listComments.do");
		result.addObject("principal", administrator);

		return result;
	}
}
