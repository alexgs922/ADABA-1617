
package utilities.validators;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import forms.RequestOfferForm;

public class FutureDateValidator implements ConstraintValidator<FutureDate, RequestOfferForm> {

	@Override
	public void initialize(final FutureDate constraintAnnotation) {
	}
	@Override
	public boolean isValid(final RequestOfferForm form, final ConstraintValidatorContext context) {
		final Date actual = new Date();
		return form.getMoment().after(actual);
	}
}
