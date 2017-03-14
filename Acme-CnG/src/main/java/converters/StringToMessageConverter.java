

package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PrivateMessageRepository;
import domain.PrivateMessage;

@Component
@Transactional
public class StringToMessageConverter implements Converter<String, PrivateMessage> {

	@Autowired
	PrivateMessageRepository messageRepository;

	@Override
	public PrivateMessage convert(String text) {
		PrivateMessage result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = messageRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
