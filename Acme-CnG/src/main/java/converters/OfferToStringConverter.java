
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Offer;

@Component
@Transactional
public class OfferToStringConverter implements Converter<Offer, String> {

	@Override
	public String convert(final Offer source) {
		String res;

		if (source == null)
			res = null;
		else
			res = String.valueOf(source.getId());

		return res;
	}

}
