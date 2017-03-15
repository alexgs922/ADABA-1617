
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
		boolean res;

		if (form.getMoment() == null)
			res = true;
		else {
			final Date actual = new Date();
			res = form.getMoment().after(actual);

		}

		return res;
	}
}
