
package com.instanceofcake.revolut.rest.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.instanceofcake.revolut.rest.domain.Transfer;
import com.instanceofcake.revolut.rest.service.TransferService;
import com.instanceofcake.revolut.rest.util.Util;

public class TransferApiServlet extends HttpServlet {

	private TransferService service = new TransferService();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Transfer> transfers = service.findAll();

		Util.buildAndSendSuccessResponse(resp, 200, Util.GSON.toJson(transfers));

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String json = Util.readInputStream(req.getInputStream());

		Transfer transfer = Util.GSON.fromJson(json, Transfer.class);

		service.transfer(transfer);

		Util.buildAndSendResponse(resp, 201, "The request is complete, and a new resource is created.",
				"Fund Transfer Completed Successfully");

	}
}
