
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.RequestOfferRepository;
import domain.RequestOffer;

@Component
@Transactional
public class StringToRequestOfferConverter implements Converter<String, RequestOffer> {

	@Autowired
	RequestOfferRepository	requestOfferRepository;


	@Override
	public RequestOffer convert(final String text) {
		RequestOffer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.requestOfferRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
