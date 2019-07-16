package com.instanceofcake.revolut.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;
import com.instanceofcake.revolut.rest.BaseApiTest;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.domain.Transfer;
import com.instanceofcake.revolut.rest.exception.NoTransferDataFoundApiException;
import com.instanceofcake.revolut.rest.util.Response;
import com.instanceofcake.revolut.rest.util.Util;

public class TransferApiTest extends BaseApiTest {


	@Test
	public void testSuccessTransferFunds() {

		String Uri = "/transfers";
		String httpMethod = "POST";
		String jsonBodyString = "{"
				+ "\r\n" + "    \r\n" + "  "
				+ "  \"fromAccountId\":321,\r\n" + "  "
				+ "  \"toAccountId\":322,\r\n"
				+ "    \"amount\":10\r\n" + 
				"}";

		String jsonString = apiServletRouter(Uri, httpMethod, jsonBodyString);

		
		Response actualResponse = Util.GSON.fromJson(jsonString, Response.class);

		assertNotNull(actualResponse);

		assertEquals(201, actualResponse.getStatusCode());
		assertEquals("The request is complete, and a new resource is created.", actualResponse.getMessage());
		assertEquals("Fund Transfer Completed Successfully", actualResponse.getDescription());
		
		Uri = "/accounts";
		httpMethod = "GET";
		jsonBodyString = "";
		 jsonString = apiServletRouter(Uri, httpMethod, jsonBodyString);

		List<Account> accounts;
		Type listType = new TypeToken<List<Account>>() {
		}.getType();
		accounts = Util.GSON.fromJson(jsonString, listType);

		Account account1 = accounts.get(0);
		assertNotNull(account1);
		assertEquals(Integer.valueOf(321), account1.getId());
		assertEquals(Integer.valueOf(790), account1.getBalance());
		assertEquals("SAVINGS", account1.getType());

		Account account2 = accounts.get(1);
		assertNotNull(account2);
		assertEquals(Integer.valueOf(322), account2.getId());
		assertEquals(Integer.valueOf(910), account2.getBalance());
		assertEquals("SAVINGS", account2.getType());


	}
	
	

	@Test
	public void testGetTransfers() {
		String Uri = "/transfers";
		String httpMethod = "POST";
		String jsonBodyString = "{"
				+ "\r\n" + "    \r\n" + "  "
				+ "  \"fromAccountId\":321,\r\n" + "  "
				+ "  \"toAccountId\":322,\r\n"
				+ "    \"amount\":10\r\n" + 
				"}";

		String jsonString = apiServletRouter(Uri, httpMethod, jsonBodyString);

		
		Response actualResponse = Util.GSON.fromJson(jsonString, Response.class);

		assertNotNull(actualResponse);

		assertEquals(201, actualResponse.getStatusCode());
		
		
		String jsonRespString = apiServletRouter(Uri, "GET", "");
		
		List<Transfer> transfers;
		Type listType = new TypeToken<List<Transfer>>() {
		}.getType();
		transfers = Util.GSON.fromJson(jsonRespString, listType);

		Transfer actual = transfers.get(0);

		assertEquals(321, actual.getFromAccountId().intValue());
		assertEquals(322, actual.getToAccountId().intValue());
		assertEquals(10, actual.getAmount().intValue());
		assertEquals("Transfer Completed Successfully", actual.getStatus());

	}
	
	@Test
	public void testNoTransferDataFoundApiException_whenNoRecordsExits() {
		String Uri = "/transfers";
		String httpMethod = "GET";

		String jsonString = apiServletRouter(Uri, httpMethod, "");

		Response actualResponse = Util.GSON.fromJson(jsonString, Response.class);

		assertNotNull(actualResponse);

		assertEquals(404, actualResponse.getStatusCode());
		assertEquals("No Data Found", actualResponse.getMessage());
		assertEquals("No Transfer Data Found", actualResponse.getDescription());

	}
	
	
	@Test
	public void testInvalidAmountApiException_whenNegativeAmountGiven() {

		String Uri = "/transfers";
		String httpMethod = "POST";
		String jsonBodyString = "{"
				+ "\r\n" + "    \r\n" + "  "
				+ "  \"fromAccountId\":321,\r\n" + "  "
				+ "  \"toAccountId\":322,\r\n"
				+ "    \"amount\":-1\r\n" + 
				"}";

		String jsonString = apiServletRouter(Uri, httpMethod, jsonBodyString);

		
		Response actualResponse = Util.GSON.fromJson(jsonString, Response.class);

		assertNotNull(actualResponse);

		assertEquals(412, actualResponse.getStatusCode());
		assertEquals("Precondition Failed", actualResponse.getMessage());
		assertEquals("Amount cannot be negative.", actualResponse.getDescription());
	}

}
