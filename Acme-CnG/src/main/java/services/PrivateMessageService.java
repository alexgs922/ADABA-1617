
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PrivateMessageRepository;
import domain.Actor;
import domain.PrivateMessage;

@Service
@Transactional
public class PrivateMessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PrivateMessageRepository	privateMessageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	// Constructors -----------------------------------------------------------

	public PrivateMessageService() {
		super();
	}

	//Simple CRUD methods ---------------------------------

	public PrivateMessage create() {
		PrivateMessage res;

		res = new PrivateMessage();
		res.setCopy(true);

		return res;
	}

	public PrivateMessage create(final Actor c) {
		PrivateMessage res;

		res = new PrivateMessage();
		res.setRecipient(c);
		res.setCopy(true);

		return res;
	}

	public PrivateMessage reconstruct(final PrivateMessage message, final BindingResult binding) {

		PrivateMessage result;
		final Actor t = this.actorService.findByPrincipal();

		result = message;
		result.setRecipient(message.getRecipient());
		result.setSender(t);
		result.setText(message.getText());
		result.setTitle(message.getTitle());
		result.setAttachments(message.getAttachments());
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setCopy(message.getCopy());
		this.validator.validate(result, binding);

		return result;

	}

	public PrivateMessage reply(final PrivateMessage message, final BindingResult binding) {

		PrivateMessage result;
		final Actor t = this.actorService.findByPrincipal();

		result = message;
		result.setRecipient(message.getRecipient());
		result.setSender(t);
		result.setText(message.getText());
		result.setTitle(message.getTitle());
		result.setAttachments(message.getAttachments());
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setCopy(message.getCopy());
		this.validator.validate(result, binding);

		return result;

	}

	public Collection<PrivateMessage> findAll() {
		Collection<PrivateMessage> res;
		res = this.privateMessageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public PrivateMessage findOne(final int messageId) {
		PrivateMessage res;
		res = this.privateMessageRepository.findOne(messageId);
		Assert.notNull(res);
		return res;
	}

	public void save(final PrivateMessage m) {
		Assert.notNull(m);

		final Actor c = this.actorService.findByPrincipal();

		final PrivateMessage message1 = new PrivateMessage();
		message1.setTitle(m.getTitle());
		message1.setText(m.getText());
		message1.setMoment(m.getMoment());
		message1.setAttachments(m.getAttachments());
		message1.setRecipient(m.getRecipient());
		message1.setSender(m.getSender());
		message1.setCopy(false);

		final PrivateMessage message2 = new PrivateMessage();
		message2.setTitle(m.getTitle());
		message2.setText(m.getText());
		message2.setMoment(m.getMoment());
		message2.setAttachments(m.getAttachments());
		message2.setRecipient(m.getRecipient());
		message2.setSender(m.getSender());
		message1.setCopy(true);

		final Collection<PrivateMessage> cr = m.getRecipient().getRecivedMessages();
		cr.add(message1);

		final Collection<PrivateMessage> cs = c.getSendedMessages();
		cs.add(message2);

		this.privateMessageRepository.save(message2);
		this.privateMessageRepository.save(message1);

	}

	public void deleteReceived2(final PrivateMessage m) {
		Assert.notNull(m);

		final Actor principal = this.actorService.findByPrincipal();
		final Collection<PrivateMessage> recibidos = this.myRecivedMessages(principal.getId());

		final Collection<PrivateMessage> todosPrincipal = new ArrayList<PrivateMessage>();
		todosPrincipal.addAll(recibidos);

		final Collection<PrivateMessage> todosSender = new ArrayList<PrivateMessage>();
		todosSender.addAll(this.mySendedMessages(m.getSender().getId()));

		Assert.isTrue(principal.getId() == m.getSender().getId() || principal.getId() == m.getRecipient().getId());
		if (recibidos.contains(m)) {
			recibidos.remove(m);
			principal.setRecivedMessages(recibidos);
			this.actorService.save(principal);

		}

		if (!todosPrincipal.contains(m) && !todosSender.contains(m))
			this.privateMessageRepository.delete(m);

	}

	public void deleteSent2(final PrivateMessage m) {
		Assert.notNull(m);

		final Actor principal = this.actorService.findByPrincipal();
		final Collection<PrivateMessage> enviados = this.mySendedMessages(principal.getId());

		final Collection<PrivateMessage> todosPrincipal = new ArrayList<PrivateMessage>();
		todosPrincipal.addAll(enviados);

		final Collection<PrivateMessage> todosRecipient = new ArrayList<PrivateMessage>();
		todosRecipient.addAll(this.mySendedMessages(m.getRecipient().getId()));

		Assert.isTrue(principal.getId() == m.getSender().getId() || principal.getId() == m.getRecipient().getId());

		if (enviados.contains(m)) {
			enviados.remove(m);
			final Collection<PrivateMessage> nuevos = new ArrayList<PrivateMessage>();
			nuevos.addAll(enviados);
			principal.setSendedMessages(nuevos);
			this.actorService.save(principal);

		}

		if (!todosPrincipal.contains(m) && !todosRecipient.contains(m))
			this.privateMessageRepository.delete(m);

	}

	public void deleteReceived(final PrivateMessage m) {
		Assert.notNull(m);
		this.privateMessageRepository.delete(m.getId());

	}

	public void deleteSent(final PrivateMessage m) {
		Assert.notNull(m);
		this.privateMessageRepository.delete(m.getId());

	}

	//Other methods ---------------------------------------------------------

	public Collection<PrivateMessage> mySendedMessages(final int actorId) {

		final Collection<PrivateMessage> sm = this.privateMessageRepository.mySendedMessages(actorId);

		return sm;
	}

	public Collection<PrivateMessage> myRecivedMessages(final int actorId) {

		final Collection<PrivateMessage> sm = this.privateMessageRepository.myRecivedMessages(actorId);

		return sm;
	}

	public void flush() {
		this.privateMessageRepository.flush();

	}

}
