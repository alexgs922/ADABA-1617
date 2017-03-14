
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PrivateMessage;

@Component
@Transactional
public class MessageToStringConverter implements Converter<PrivateMessage, String> {

	@Override
	public String convert(final PrivateMessage message) {
		String result;

		if (message == null)
			result = null;
		else
			result = String.valueOf(message.getId());

		return result;
	}

}
