
package services;

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

		return res;
	}

	public PrivateMessage create(final Actor c) {
		PrivateMessage res;

		res = new PrivateMessage();
		res.setRecipient(c);

		return res;
	}

	public PrivateMessage reconstruct(final PrivateMessage message, final BindingResult binding) {

		PrivateMessage result;
		final Actor t = this.actorService.findByPrincipal();

		result = message;
		result.setRecipient(message.getRecipient());
		result.setSender(t);
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
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

	public PrivateMessage save(final PrivateMessage m) {
		Assert.notNull(m);
		return this.privateMessageRepository.save(m);

	}

	public void delete(final PrivateMessage m) {
		Assert.notNull(m);
		this.privateMessageRepository.delete(m);
	}

}
