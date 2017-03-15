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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PrivateMessageService;
import domain.Actor;
import domain.PrivateMessage;

@Controller
@RequestMapping("/message")
public class PrivateMessageController extends AbstractController {

	@Autowired
	private PrivateMessageService	privateMessageService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public PrivateMessageController() {
		super();
	}

	@RequestMapping(value = "/listReceivedMessages", method = RequestMethod.GET)
	public ModelAndView listReceivedMessages() {
		ModelAndView result;

		final Actor c = this.actorService.findByPrincipal();

		final Collection<PrivateMessage> receivedMessages = c.getRecivedMessages();

		result = new ModelAndView("message/listReceivedMessages");
		result.addObject("messages", receivedMessages);
		result.addObject("requestURI", "message/listReceivedMessages.do");

		return result;
	}

	@RequestMapping(value = "/listSentMessages", method = RequestMethod.GET)
	public ModelAndView listSentMessages() {
		ModelAndView result;

		final Actor c = this.actorService.findByPrincipal();

		final Collection<PrivateMessage> sendMessages = c.getSendedMessages();

		result = new ModelAndView("message/listSentMessages");
		result.addObject("messages", sendMessages);
		result.addObject("requestURI", "message/listSentMessages.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PrivateMessage message;

		message = this.privateMessageService.create();

		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/response/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int actorId) {
		ModelAndView result;
		PrivateMessage message;

		final Actor c = this.actorService.findOneToSent(actorId);
		message = this.privateMessageService.create(c);

		result = this.createEditModelAndView2(message);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PrivateMessage messageToEdit, final BindingResult binding) {

		ModelAndView result;
		final Actor c = this.actorService.findByPrincipal();

		messageToEdit = this.privateMessageService.reconstruct(messageToEdit, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(messageToEdit);

			result.addObject("privateMessage", messageToEdit);

		} else {
			try {

				this.privateMessageService.save(messageToEdit);
				final Collection<PrivateMessage> cr = messageToEdit.getRecipient().getRecivedMessages();
				cr.add(messageToEdit);
				final Collection<PrivateMessage> cs = c.getSendedMessages();
				cs.add(messageToEdit);

			} catch (final Throwable th) {
				result = this.createEditModelAndView(messageToEdit, "message.commit.error");

				result.addObject("privateMessage", messageToEdit);

				return result;
			}

			result = new ModelAndView("redirect:/message/listSentMessages.do");

		}

		return result;
	}

	@RequestMapping(value = "/response/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save2(PrivateMessage messageToEdit, final BindingResult binding) {

		ModelAndView result;
		final Actor c = this.actorService.findByPrincipal();

		messageToEdit = this.privateMessageService.reconstruct(messageToEdit, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView2(messageToEdit);

			result.addObject("privateMessage", messageToEdit);

		} else {
			try {

				this.privateMessageService.save(messageToEdit);
				final Collection<PrivateMessage> cr = messageToEdit.getRecipient().getRecivedMessages();
				cr.add(messageToEdit);
				final Collection<PrivateMessage> cs = c.getSendedMessages();
				cs.add(messageToEdit);

			} catch (final Throwable th) {
				result = this.createEditModelAndView2(messageToEdit, "message.commit.error");

				result.addObject("privateMessage", messageToEdit);

				return result;
			}

			result = new ModelAndView("redirect:/message/listSentMessages.do");

		}

		return result;
	}

	//CREATE EDIT MODEL AND VIEW

	protected ModelAndView createEditModelAndView(final PrivateMessage messageToEdit) {
		ModelAndView result;

		result = this.createEditModelAndView(messageToEdit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PrivateMessage messageToEdit, final String message) {
		ModelAndView result;

		Collection<Actor> actors;
		final Actor c = this.actorService.findByPrincipal();

		actors = this.actorService.findAll();
		actors.remove(c);

		result = new ModelAndView("message/create");
		result.addObject("privateMessage", messageToEdit);
		result.addObject("actors", actors);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final PrivateMessage messageToEdit) {
		ModelAndView result;

		result = this.createEditModelAndView2(messageToEdit, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final PrivateMessage messageToEdit, final String message) {
		ModelAndView result;

		result = new ModelAndView("message/response/create");
		result.addObject("privateMessage", messageToEdit);
		result.addObject("message", message);

		return result;
	}

}
