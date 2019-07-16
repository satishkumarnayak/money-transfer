package com.instanceofcake.revolut.rest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instanceofcake.revolut.rest.exception.ApiException;


public class Util {

	public static String readInputStream(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining("\n"));
	}

	public static final Gson GSON = new GsonBuilder().create();

	public static void buildAndSendResponse(HttpServletResponse httpResp, int statusCode, String msg, String desc) throws IOException {
		Response responseBody = new Response(statusCode, msg, desc);
		httpResp.addHeader("Content-Type", "application/json");
		httpResp.setStatus(statusCode);
		httpResp.getWriter().println(Util.GSON.toJson(responseBody));
	}
	
	public static void buildAndSendErrorResponse(HttpServletResponse httpResp, ApiException apiException) throws IOException {
		Response responseBody = new Response(apiException.getStatusCode(), apiException.getMessage(), apiException.getDescription());
		httpResp.addHeader("Content-Type", "application/json");
		httpResp.setStatus(apiException.getStatusCode());
		httpResp.getWriter().println(Util.GSON.toJson(responseBody));
	}
	
	public static void buildAndSendErrorResponse(HttpServletResponse httpResp, Exception exception) throws IOException {
		Response responseBody = new Response(500, "Internal Server Error", exception.getMessage());
		httpResp.addHeader("Content-Type", "application/json");
		httpResp.setStatus(500);
		httpResp.getWriter().println(Util.GSON.toJson(responseBody));
	}
	
	public static void buildAndSendSuccessResponse(HttpServletResponse httpResp, int statusCode, String jsonBody) throws IOException {

		httpResp.addHeader("Content-Type", "application/json");
		httpResp.setStatus(statusCode);
		httpResp.getWriter().println(jsonBody);
	}
}

