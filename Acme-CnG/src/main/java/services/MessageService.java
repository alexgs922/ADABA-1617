
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	//Simple CRUD methods ---------------------------------

	//TODO falta hacer el create

	public Collection<Message> findAll() {
		Collection<Message> res;
		res = this.messageRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Message findOne(final int messageId) {
		Message res;
		res = this.messageRepository.findOne(messageId);
		Assert.notNull(res);
		return res;
	}

	public Message save(final Message m) {
		Assert.notNull(m);
		return this.messageRepository.save(m);

	}

	public void delete(final Message m) {
		Assert.notNull(m);
		this.messageRepository.delete(m);
	}

}
