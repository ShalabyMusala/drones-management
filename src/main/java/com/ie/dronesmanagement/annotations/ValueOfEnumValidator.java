package com.ie.dronesmanagement.annotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.http.HttpStatus;

import com.ie.dronesmanagement.exception.BackendException;

public class ValueOfEnumValidator implements ConstraintValidator<IsValueOfEnum, String> {

	private String message;
	private List<String> acceptedValues;

	@Override
	public void initialize(IsValueOfEnum annotation) {
		acceptedValues = Stream.of(annotation.enumClass().getEnumConstants()).map(Enum::name)
				.collect(Collectors.toList());
		message = annotation.message();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value != null && (!acceptedValues.contains(value) || value.trim().isEmpty())) {
			throw new BackendException(message, 4000, message, HttpStatus.UNPROCESSABLE_ENTITY.value());
		}

		return true;
	}

}
