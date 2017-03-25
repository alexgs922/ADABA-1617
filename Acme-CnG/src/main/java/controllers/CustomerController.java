/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Comment;
import domain.CommentableEntity;
import domain.Customer;
import forms.RegistrationForm;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerService	customerService;


	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	// Profile ---------------------------------------------------------------

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int customerId) {
		ModelAndView result;
		CommentableEntity customer;
		final Collection<Comment> comments;

		customer = this.customerService.findOne(customerId);

		comments = new ArrayList<Comment>();

		for (final Comment co : customer.getCommentsReceived())
			if (co.isBanned() == false)
				comments.add(co);

		result = new ModelAndView("customer/profile");
		result.addObject("requestURI", "customer/profile.do");
		result.addObject("customer", customer);
		result.addObject("comments", comments);

		return result;
	}

	// Terms of Use -----------------------------------------------------------

	@RequestMapping("/dataProtection")
	public ModelAndView dataProtection() {
		ModelAndView result;

		result = new ModelAndView("customer/dataProtection");

		return result;
	}
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		RegistrationForm customer;

		customer = new RegistrationForm();

		result = this.createEditModelAndView(customer);

		return result;
	}
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("customer") @Valid final RegistrationForm form, final BindingResult binding) {
		Customer customer;
		ModelAndView result;

		if (binding.hasErrors()) {

			if (binding.getGlobalError() != null)
				result = this.createEditModelAndView(form, binding.getGlobalError().getCode());
			else
				result = this.createEditModelAndView(form);
		} else
			try {
				customer = this.customerService.reconstruct(form);
				this.customerService.save(customer);
				result = new ModelAndView("redirect:../security/login.do");

			} catch (final DataIntegrityViolationException exc) {
				result = this.createEditModelAndView(form, "customer.duplicated.user");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(form, "customer.commit.error");
			}

		return result;

	}

	//---

	protected ModelAndView createEditModelAndView(final RegistrationForm customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final RegistrationForm customer, final String message) {
		ModelAndView result;

		result = new ModelAndView("customer/register");
		result.addObject("customer", customer);
		result.addObject("message", message);

		return result;
	}

}
