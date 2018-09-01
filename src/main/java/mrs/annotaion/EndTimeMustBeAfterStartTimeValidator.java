package mrs.annotaion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import mrs.app.reservation.ReservationForm;

public class EndTimeMustBeAfterStartTimeValidator implements ConstraintValidator<EndTimeMustBeAfterStartTime, ReservationForm> {

	private String message;

	@Override
	public void initialize(EndTimeMustBeAfterStartTime constraintAnnotation) {
		message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(ReservationForm value, ConstraintValidatorContext context) {

		if(value == null)
			return true;
		else if(value.getStartTime() == null || value.getEndTime() == null)
			return true;

		boolean isAfterStartTime = value.getEndTime().isAfter(value.getStartTime());

		if(!isAfterStartTime) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode("endTime").addConstraintViolation();
		}

		return isAfterStartTime;
	}

}
