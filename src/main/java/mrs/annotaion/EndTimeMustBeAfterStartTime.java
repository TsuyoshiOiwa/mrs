package mrs.annotaion;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, ANNOTATION_TYPE })
@Constraint(validatedBy= {EndTimeMustBeAfterStartTimeValidator.class})
public @interface EndTimeMustBeAfterStartTime {
	String message() default "mrs.annotation.EndTimeMustBeAfterStartTime.message";

	Class<?>[]groups() default {};

	Class<? extends Payload>[] payload() default {};


	@Documented
	@Retention(RUNTIME)
	@Target({ TYPE, ANNOTATION_TYPE })
	public @interface List{
		EndTimeMustBeAfterStartTime[] value();
	}
}
