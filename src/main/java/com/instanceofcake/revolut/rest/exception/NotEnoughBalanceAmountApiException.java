package com.instanceofcake.revolut.rest.exception;

public class NotEnoughBalanceAmountApiException extends ApiException {

	public NotEnoughBalanceAmountApiException(Integer statusCode, String message, String description) {
		super(statusCode, message, description);
	}

}
