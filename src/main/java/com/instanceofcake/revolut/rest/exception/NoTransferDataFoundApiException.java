package com.instanceofcake.revolut.rest.exception;

public class NoTransferDataFoundApiException extends ApiException {

	public NoTransferDataFoundApiException(Integer statusCode, String message, String description) {
		super(statusCode, message, description);
	}

}
