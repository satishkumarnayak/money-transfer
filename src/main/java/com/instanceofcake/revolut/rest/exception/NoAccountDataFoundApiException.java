package com.instanceofcake.revolut.rest.exception;

public class NoAccountDataFoundApiException extends ApiException {

	public NoAccountDataFoundApiException(Integer statusCode, String message, String description) {
		super(statusCode, message, description);
	}

}
