
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.RequestOfferService;
import controllers.AbstractController;
import domain.Administrator;
import domain.RequestOffer;
import domain.RequestOrOffer;

@Controller
@RequestMapping("/requestOffer/administrator")
public class AdministratorRequestOfferController extends AbstractController {

	//Services -----------------------------------------------------------------
	@Autowired
	private RequestOfferService		requestOfferService;

	@Autowired
	private AdministratorService	administratorService;


	//Methods ------------------------------------------------------------------

	@RequestMapping(value = "/listRequests", method = RequestMethod.GET)
	public ModelAndView listAllRequests() {
		ModelAndView result;
		Collection<RequestOffer> requests;
		Administrator administrator;

		requests = this.requestOfferService.findAllRequest();
		administrator = this.administratorService.findByPrincipal();

		result = new ModelAndView("requestOffer/administrator/listRequests");
		result.addObject("requestOffers", requests);
		result.addObject("requestURI", "requestOffer/administrator/listRequests.do");
		result.addObject("principal", administrator);

		return result;
	}
	@RequestMapping(value = "/listOffers", method = RequestMethod.GET)
	public ModelAndView listAllOffers() {
		ModelAndView result;
		Collection<RequestOffer> offers;
		Administrator administrator;

		offers = this.requestOfferService.findAllOffers();
		administrator = this.administratorService.findByPrincipal();

		result = new ModelAndView("requestOffer/administrator/listOffers");
		result.addObject("requestOffers", offers);
		result.addObject("requestURI", "requestOffer/administrator/listOffers.do");
		result.addObject("principal", administrator);

		return result;
	}

	@RequestMapping(value = "/banRequestOffer", method = RequestMethod.GET)
	public ModelAndView banRequestOffer(@RequestParam final int requestOfferId) {
		ModelAndView result;
		RequestOffer requestOffer;

		requestOffer = this.requestOfferService.findOne(requestOfferId);

		this.requestOfferService.banRequestOffer(requestOffer);

		if (requestOffer.getRequestOrOffer() == RequestOrOffer.REQUEST)
			result = new ModelAndView("redirect:listRequests.do");
		else
			result = new ModelAndView("redirect:listOffers.do");

		return result;

	}

}
