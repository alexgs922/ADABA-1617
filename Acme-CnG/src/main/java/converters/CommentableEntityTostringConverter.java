
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.CommentableEntity;

@Component
@Transactional
public class CommentableEntityTostringConverter implements Converter<CommentableEntity, String> {

	@Override
	public String convert(CommentableEntity source) {
		String res;

		if (source == null) {
			res = null;
		} else {
			res = String.valueOf(source.getId());
		}

		return res;
	}

}
