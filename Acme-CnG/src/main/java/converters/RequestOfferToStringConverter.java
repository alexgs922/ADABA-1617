
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class RequestOfferToStringConverter implements Converter<RequestOffer, String> {

	@Override
	public String convert(final RequestOffer source) {
		String res;

		if (source == null)
			res = null;
		else
			res = String.valueOf(source.getId());

		return res;
	}

}
