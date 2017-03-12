package converters;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import domain.Comment;

@Component
@Transactional
public class CommentToStringConverter implements Converter<Comment, String> {

	@Override
	public String convert(Comment source) {
		String res;

		if (source == null) {
			res = null;
		} else {
			res = String.valueOf(source.getId());
		}

		return res;
	}

}