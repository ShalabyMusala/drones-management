package com.ie.dronesmanagement.exception;

public class BackendException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int httpCode;
	private int code;
	private String reason;

	public BackendException() {
		super();
	}

	public BackendException(String message, int code, String reason, int httpCode) {
		super(message);
		this.httpCode = httpCode;
		this.code = code;
		this.reason = reason;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
