package com.ie.dronesmanagement.exception;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.ValidationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ie.dronesmanagement.model.APIResponse;
import com.ie.dronesmanagement.model.APIResponseBuilder;
import com.ie.dronesmanagement.util.Constants;

@RestControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

	HttpHeaders customHeaders = new HttpHeaders();

	@PostConstruct
	private void init() {
		customHeaders.setContentType(MediaType.APPLICATION_JSON);

	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				new APIResponseBuilder<>().code(Constants.UNPROCESSABLE_ENTITY_CODE).addMessage(ex.getMessage())
						.reason(Constants.UNPROCESSABLE_ENTITY_MESSAGE).build(),
				customHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				new APIResponseBuilder<>().code(Constants.UNPROCESSABLE_ENTITY_CODE).addMessage("Type Mismatch")
						.reason(Constants.UNPROCESSABLE_ENTITY_MESSAGE).build(),
				customHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

		return new ResponseEntity<>(
				new APIResponseBuilder<>().code(Constants.UNPROCESSABLE_ENTITY_CODE)
						.addMessage(fieldErrors.get(0).getDefaultMessage()).reason("Invalid Input Request").build(),
				customHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				new APIResponseBuilder<>().code(Constants.UNPROCESSABLE_ENTITY_CODE).addMessage(ex.getMessage())
						.reason(Constants.UNPROCESSABLE_ENTITY_MESSAGE).build(),
				customHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(new APIResponseBuilder<>().code(4005).addMessage(ex.getLocalizedMessage())
				.reason("Method not allowed").build(), customHeaders, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String missingParam = ex.getParameter().getParameterName();
		String errorMessage = "Missing " + missingParam + " path variable";

		return new ResponseEntity<>(
				new APIResponseBuilder<>().code(Constants.UNPROCESSABLE_ENTITY_CODE).addMessage(errorMessage)
						.reason(Constants.UNPROCESSABLE_ENTITY_MESSAGE).build(),
				customHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				new APIResponseBuilder<>().code(4004).addMessage("Invalid url").reason("Not found").build(),
				customHeaders, status.value());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(new APIResponseBuilder<>().code(4015).addMessage(ex.getMessage())
				.reason("Unsupported media type").build(), customHeaders, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return new ResponseEntity<>(
				new APIResponseBuilder<>().code(4006).addMessage(ex.getMessage()).reason("Not acceptable").build(),
				customHeaders, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<Object> handleViolationException(Exception ex, WebRequest request) {

		return new ResponseEntity<>(new APIResponseBuilder<>().code(Constants.UNPROCESSABLE_ENTITY_CODE)
				.addMessage(ex.getMessage()).reason("Invalid Input Request").build(), customHeaders,
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler({ BackendException.class })
	public ResponseEntity<APIResponse<Object>> handleBackEndException(BackendException ex, WebRequest request) {
		return new ResponseEntity<>(new APIResponseBuilder<>().code(ex.getCode()).addMessage(ex.getMessage())
				.reason(ex.getReason()).build(), customHeaders, ex.getHttpCode());

	}

}
