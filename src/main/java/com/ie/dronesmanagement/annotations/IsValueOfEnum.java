package com.ie.dronesmanagement.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValueOfEnumValidator.class })
public @interface IsValueOfEnum {

	String message() default "String should be valid value of enum";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Class<? extends Enum<?>> enumClass();

}
