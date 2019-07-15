package com.instanceofcake.revolut.rest.exception;

public class InsufficientFundsApiException extends ApiException {

	public InsufficientFundsApiException(Integer statusCode, String message, String description) {
		super(statusCode, message, description);
	}

}
