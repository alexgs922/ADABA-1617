
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.ApplicationService;
import services.CustomerService;
import services.RequestOfferService;

@Controller
@RequestMapping("/requestOffer")
public class RequestOfferController extends AbstractController {

	//Services ---------------------------------------------------------------

	@Autowired
	private RequestOfferService	requestOfferService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ApplicationService	applicationService;

	//Methods ----------------------------------------------------------------

}
