
package acme.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({
	ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPercentageIncreaseValidator.class)
public @interface ValidPercentageIncrease {

	String message() default "{acme.validation.tracking.percentage.invalid}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
