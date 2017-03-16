
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Application;
import domain.Customer;

@Controller
@RequestMapping("/application/customer")
public class CustomerApplicationController extends AbstractController {

	//Services --------------------------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;


	//Methods ----------------------------------------------------------------------------

	@RequestMapping(value = "/listMyApplications", method = RequestMethod.GET)
	public ModelAndView listListMyApplications() {
		ModelAndView result;
		Collection<Application> applications;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		applications = this.applicationService.findCustomerApplications(customer);

		result = new ModelAndView("application/customer/listMyApplications");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/listMyApplications.do");
		result.addObject("principal", customer);

		return result;
	}

	@RequestMapping(value = "/listMyRequestOfferApplications", method = RequestMethod.GET)
	public ModelAndView listListMyRequestOfferApplications() {
		ModelAndView result;
		Collection<Application> applications;
		Customer customer;

		customer = this.customerService.findByPrincipal();
		applications = this.applicationService.findCustomerRequestOfferApplications(customer);

		result = new ModelAndView("application/customer/listMyRequestOfferApplications");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/listMyRequestOfferApplications.do");
		result.addObject("principal", customer);

		return result;
	}

}
