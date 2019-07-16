package com.instanceofcake.revolut.rest.router;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.instanceofcake.revolut.rest.api.AccountApiServlet;
import com.instanceofcake.revolut.rest.api.TransferApiServlet;
import com.instanceofcake.revolut.rest.exception.ApiException;
import com.instanceofcake.revolut.rest.exception.InvalidResourceApiException;
import com.instanceofcake.revolut.rest.util.Util;

public class ApiRouterServlet extends HttpServlet {

	private static final TransferApiServlet TRANSFER_API = new TransferApiServlet();
	private static final AccountApiServlet ACCOUNT_API = new AccountApiServlet();

	private String requestURI;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			requestURI = req.getRequestURI();
			if (requestURI.trim().startsWith("/accounts")) {
				ACCOUNT_API.doGet(req, resp);
			} else if (requestURI.startsWith("/transfers")) {
				TRANSFER_API.doGet(req, resp);
			} else {
				noHandlerException(resp);
			}
		} catch (ApiException apiException) {
			Util.buildAndSendErrorResponse(resp, apiException);
		} catch (Exception exception) {
			Util.buildAndSendErrorResponse(resp, exception);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			requestURI = req.getRequestURI();
			if (requestURI.startsWith("/transfers")) {
				TRANSFER_API.doPost(req, resp);
			} else {
				noHandlerException(resp);
			}

		} catch (ApiException apiException) {
			Util.buildAndSendErrorResponse(resp, apiException);
		} catch (Exception exception) {
			Util.buildAndSendErrorResponse(resp, exception);
		}
	}

	private void noHandlerException(HttpServletResponse resp) throws IOException {
		throw new InvalidResourceApiException(405, "Method Not Allowed", "Invalid Resource Requested");
	}

}
