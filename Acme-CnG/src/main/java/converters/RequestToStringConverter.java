package converters;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import domain.Request;

@Component
@Transactional
public class RequestToStringConverter implements Converter<Request, String> {

	@Override
	public String convert(Request source) {
		String res;

		if (source == null) {
			res = null;
		} else {
			res = String.valueOf(source.getId());
		}

		return res;
	}

}