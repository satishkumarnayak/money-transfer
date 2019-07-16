package com.instanceofcake.revolut.rest.util;

import javax.servlet.http.HttpServletResponse;

public class Response {
	
	int statusCode;
	String message;
	String description;
	
	
	public Response(HttpServletResponse resp) {
		
	}
	
	public Response(int statusCode, String message, String description) {
		super();
		
		this.statusCode = statusCode;
		this.message = message;
		this.description = description;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", message=" + message + ", description=" + description + "]";
	}
	
	
	
	
	
	

}
