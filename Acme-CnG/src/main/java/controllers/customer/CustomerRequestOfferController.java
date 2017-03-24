
package controllers.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.RequestOfferService;
import controllers.AbstractController;
import domain.Actor;
import domain.Application;
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


	
	@RequestMapping(value = "/listRequests", method = RequestMethod.GET)
	public ModelAndView listAllRequests() {
		ModelAndView result;
		Collection<RequestOffer> requests;
		Customer customer;
		Collection<Application> applications;

		requests = this.requestOfferService.findAllRequestNotBanned();
		customer = this.customerService.findByPrincipal();
		applications = customer.getApplications();

		result = new ModelAndView("requestOffer/customer/listRequests");
		result.addObject("requestOffers", requests);
		result.addObject("requestURI", "requestOffer/customer/listRequests.do");
		result.addObject("principal", customer);
		result.addObject("applications", applications);

		return result;
	}
	@RequestMapping(value = "/listOffers", method = RequestMethod.GET)
	public ModelAndView listAllOffers() {
		ModelAndView result;
		Collection<RequestOffer> offers;
		Customer customer;
		Collection<Application> applications;

		offers = this.requestOfferService.findAllOffersNotBanned();
		customer = this.customerService.findByPrincipal();
		applications = customer.getApplications();

		result = new ModelAndView("requestOffer/customer/listOffers");
		result.addObject("requestOffers", offers);
		result.addObject("requestURI", "requestOffer/customer/listOffers.do");
		result.addObject("principal", customer);
		result.addObject("applications", applications);

		return result;
	}

	@RequestMapping(value = "/applyRequest", method = RequestMethod.GET)
	public ModelAndView applyRequest(@RequestParam final int requestOfferId) {
		ModelAndView result;
		RequestOffer requestOffer;

		requestOffer = this.requestOfferService.findOne(requestOfferId);

		this.requestOfferService.applyRequestOffer(requestOffer);

		result = new ModelAndView("redirect:listRequests.do");

		return result;
	}

	@RequestMapping(value = "/applyOffer", method = RequestMethod.GET)
	public ModelAndView applyOffer(@RequestParam final int requestOfferId) {
		ModelAndView result;
		RequestOffer requestOffer;

		requestOffer = this.requestOfferService.findOne(requestOfferId);

		this.requestOfferService.applyRequestOffer(requestOffer);

		result = new ModelAndView("redirect:listOffers.do");

		return result;
	}

	@RequestMapping(value = "/myRequestsandOffers", method = RequestMethod.GET)
	public ModelAndView listRequests() {
		ModelAndView result;
		Collection<RequestOffer> requests;
		Collection<RequestOffer> offers;
		Collection<RequestOffer> banned;
		Collection<Application> applications;

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
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView search(){
		ModelAndView result;
		List<RequestOffer> requests;
		Customer principal;
		Collection<Application> applications;
		
		principal = this.customerService.findByPrincipal();

		
		Collection<RequestOffer> offerNotBanned = this.requestOfferService.findAllOffersNotBanned();
		Collection<RequestOffer> requestNotBanned = this.requestOfferService.findAllRequestNotBanned();
		
		requests = new ArrayList<RequestOffer>(offerNotBanned);
		requests.addAll(requestNotBanned);
		
		applications = principal.getApplications();
		
		result = new ModelAndView("requestOffer/customer/search");

		result.addObject("requestOffers", requests);
		result.addObject("requestURI", "requestOffer/customer/listRequests.do");
		result.addObject("applications", applications);
		
		return result;
	}
	
	@RequestMapping(value="/listByKeyword", method=RequestMethod.GET)
	public ModelAndView listByKeyword(@RequestParam String keyword){
		ModelAndView result;
		Collection<RequestOffer> requestOffer;
		requestOffer = this.requestOfferService.findRequestOfferByKeyword(keyword);
		result = new ModelAndView("requestOffer/customer/search");
		result.addObject("requestURI", "recipe/listByKeyword.do");
		result.addObject("requestOffers", requestOffer);
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
