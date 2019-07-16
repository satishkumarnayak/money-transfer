package com.instanceofcake.revolut.rest.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.service.AccountService;
import com.instanceofcake.revolut.rest.util.Util;

public class AccountApiServlet extends HttpServlet {

	static final Gson GSON = new GsonBuilder().create();
	private AccountService service = new AccountService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String requestURI = req.getRequestURI();
		List<Account> accounts = new ArrayList<Account>();
		if (requestURI.equals("/accounts")) {

			accounts = service.getAllAccounts();

		}

		Util.buildAndSendSuccessResponse(resp, 200, GSON.toJson(accounts));

	}

}
