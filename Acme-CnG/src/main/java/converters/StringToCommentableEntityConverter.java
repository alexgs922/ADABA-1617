
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.CommentableEntityRepository;
import domain.CommentableEntity;

@Component
@Transactional
public class StringToCommentableEntityConverter implements Converter<String, CommentableEntity> {

	@Autowired
	CommentableEntityRepository	commentRepository;


	@Override
	public CommentableEntity convert(String text) {
		CommentableEntity result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = commentRepository.findOneAlternativo(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
