package com.instanceofcake.revolut.rest.exception;

public class InvalidResourceApiException extends ApiException {

	public InvalidResourceApiException(Integer statusCode, String message, String description) {
		super(statusCode, message, description);
	}

}
