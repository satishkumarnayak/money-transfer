package com.instanceofcake.revolut.rest.exception;

public class ToandFromSameAccountApiException extends ApiException {

	public ToandFromSameAccountApiException(Integer statusCode, String message, String description) {
		super(statusCode, message, description);
	}

}
