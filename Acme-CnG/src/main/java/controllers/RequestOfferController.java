
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RequestOfferService;
import domain.RequestOffer;

@Controller
@RequestMapping("/requestOffer")
public class RequestOfferController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private RequestOfferService	requestOfferService;


	//Methods ----------------------------------------------------------------

	@RequestMapping(value = "/listRequests", method = RequestMethod.GET)
	public ModelAndView listRequests() {
		ModelAndView result;
		Collection<RequestOffer> requests;

		requests = this.requestOfferService.findAllRequest();

		result = new ModelAndView("requestOffer/listRequests");
		result.addObject("requestOffers", requests);
		result.addObject("requestURI", "requestOffer/listRequests.do");

		return result;
	}

	@RequestMapping(value = "/listOffers", method = RequestMethod.GET)
	public ModelAndView listOffers() {
		ModelAndView result;
		Collection<RequestOffer> offers;

		offers = this.requestOfferService.findAllOffers();

		result = new ModelAndView("requestOffer/listOffers");
		result.addObject("requestOffers", offers);
		result.addObject("requestURI", "requestOffer/listOffers.do");

		return result;
	}

}
