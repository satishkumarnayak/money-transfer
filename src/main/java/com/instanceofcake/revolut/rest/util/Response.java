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
	
	
	
	

}
