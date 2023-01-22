package com.ie.dronesmanagement.model;

import java.util.ArrayList;
import java.util.List;

public class APIResponseBuilder<Object> {

	private APIResponse<Object> apiResponse;

	public APIResponseBuilder() {

		apiResponse = new APIResponse<>();
	}

	public APIResponseBuilder<Object> body(Object data) {

		this.apiResponse.setData(data);

		return this;
	}

	public APIResponseBuilder<Object> messages(List<String> messages) {

		this.apiResponse.setMessage(messages);

		return this;
	}

	public APIResponseBuilder<Object> emptyMessages() {

		List<String> messages = new ArrayList<>();

		this.apiResponse.setMessage(messages);

		return this;
	}

	public APIResponseBuilder<Object> code(int code) {

		this.apiResponse.setCode(code);

		return this;
	}

	public APIResponseBuilder<Object> reason(String reason) {

		this.apiResponse.setReason(reason);

		return this;
	}

	public APIResponseBuilder<Object> addMessage(String message) {

		if (this.apiResponse.getMessage() == null)
			emptyMessages();

		this.apiResponse.getMessage().add(message);

		return this;
	}

	public APIResponseBuilder<Object> addMessages(List<String> messages) {

		if (this.apiResponse.getMessage() == null)
			emptyMessages();

		this.apiResponse.getMessage().addAll(messages);

		return this;
	}

	public APIResponse<Object> build() {

		return this.apiResponse;
	}

}
