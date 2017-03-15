
package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.RequestOfferService;
import controllers.AbstractController;
import domain.Customer;
import domain.RequestOffer;
import domain.RequestOrOffer;
import forms.RequestOfferForm;

@Controller
@RequestMapping("/requestOffer/customer")
public class CustomerRequestOfferController extends AbstractController {

	//Services --------------------------------------------------------------

	@Autowired
	private RequestOfferService	requestOfferService;

	@Autowired
	private CustomerService		customerService;


	//Methods ---------------------------------------------------------------

	@RequestMapping(value = "/myRequestsandOffers", method = RequestMethod.GET)
	public ModelAndView listRequests() {
		ModelAndView result;
		Collection<RequestOffer> requests;
		Collection<RequestOffer> offers;
		Collection<RequestOffer> banned;
		Customer principal;

		principal = this.customerService.findByPrincipal();

		requests = this.requestOfferService.findAllRequestFromPrincipal(principal);
		offers = this.requestOfferService.findAllOffersFromPrincipal(principal);
		banned = this.requestOfferService.findAllBannedRequestsOffersFromPrincipal(principal);

		result = new ModelAndView("requestOffer/listRequestsAndOffers");
		result.addObject("requests", requests);
		result.addObject("offers", offers);
		result.addObject("banned", banned);

		return result;
	}
	// Register ------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final RequestOfferForm requestOffer;

		requestOffer = new RequestOfferForm();

		result = new ModelAndView("requestOffer/edit");
		result.addObject("requestOffer", requestOffer);
		result.addObject("request", RequestOrOffer.REQUEST);
		result.addObject("offer", RequestOrOffer.OFFER);
		result.addObject("requestURI", "requestOffer/customer/create.do");

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("requestOffer") @Valid final RequestOfferForm requestOfferForm, final BindingResult binding) {
		ModelAndView result;
		RequestOffer requestOffer;

		if (binding.hasErrors()) {

			if (binding.getGlobalError() != null)
				result = this.createEditModelAndView(requestOfferForm, binding.getGlobalError().getCode());
			else
				result = this.createEditModelAndView(requestOfferForm);

		} else
			try {

				requestOffer = this.requestOfferService.reconstruct(requestOfferForm);
				this.requestOfferService.save(requestOffer);
				result = new ModelAndView("redirect:myRequestsandOffers.do");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(requestOfferForm, "requestOffer.commit.error");
			}

		return result;
	}

	//-------------------------------------------------------------------------------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final RequestOfferForm form, final String message) {
		ModelAndView result;

		result = new ModelAndView("requestOffer/edit");
		result.addObject("requestOffer", form);
		result.addObject("request", RequestOrOffer.REQUEST);
		result.addObject("offer", RequestOrOffer.OFFER);
		result.addObject("requestURI", "requestOffer/customer/create.do");
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RequestOfferForm form) {
		ModelAndView result;

		result = this.createEditModelAndView(form, null);

		return result;
	}

}
