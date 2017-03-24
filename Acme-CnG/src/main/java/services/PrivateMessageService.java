
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
		Actor c1 = actorService.findByPrincipal();

		res = new PrivateMessage();
		//Assert.isTrue(c.getId() == c1.getId());
		
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

		//Assert.isTrue(c.getId() == m.getSender().getId());
		
		this.privateMessageRepository.save(message2);
		this.privateMessageRepository.save(message1);

	}


	
	public void save2(final PrivateMessage m) {
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

		Assert.isTrue(c.getId() == m.getRecipient().getId());
		
		this.privateMessageRepository.save(message2);
		this.privateMessageRepository.save(message1);

	}


	
	
	
	public void deleteReceived(final PrivateMessage m) {
		Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(m);
		
		Assert.isTrue(principal.getId() == m.getRecipient().getId());
		
		this.privateMessageRepository.delete(m.getId());

	}

	public void deleteSent(final PrivateMessage m) {
		Assert.notNull(m);
		Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == m.getSender().getId());
		
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
