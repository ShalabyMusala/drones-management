package com.ie.dronesmanagement.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class APIResponse<Object> {

	private int code;
	private List<String> message;
	private String reason;
	private Object data;

	public APIResponse() {
	}

	/**
	 * @param code
	 * @param message
	 * @param reason
	 * @param data
	 */
	public APIResponse(int code, List<String> message, String reason, Object data) {
		this.code = code;
		this.message = message;
		this.reason = reason;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
