package com.instanceofcake.revolut.rest.exception;

public class InvalidAmountApiException extends ApiException {

	public InvalidAmountApiException(Integer statusCode, String message, String description) {
		super(statusCode, message, description);
	}

}
