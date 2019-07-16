package com.instanceofcake.revolut.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.List;

import org.junit.Test;

import com.google.gson.reflect.TypeToken;
import com.instanceofcake.revolut.rest.BaseApiTest;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.util.Util;

public class AccountApiTest extends BaseApiTest {

	@Test
	public void testGetAccounts() {

		String Uri;
		String httpMethod;
		String jsonBodyString;

		Uri = "/accounts";
		httpMethod = "GET";
		jsonBodyString = "";
		String jsonString = apiServletRouter(Uri, httpMethod, jsonBodyString);

		List<Account> accounts;
		Type listType = new TypeToken<List<Account>>() {
		}.getType();
		accounts = Util.GSON.fromJson(jsonString, listType);

		Account account1 = accounts.get(0);
		assertNotNull(account1);
		assertEquals(Integer.valueOf(321), account1.getId());
		assertEquals(Integer.valueOf(800), account1.getBalance());
		assertEquals("SAVINGS", account1.getType());

		Account account2 = accounts.get(1);
		assertNotNull(account2);
		assertEquals(Integer.valueOf(322), account2.getId());
		assertEquals(Integer.valueOf(900), account2.getBalance());
		assertEquals("SAVINGS", account2.getType());

		

	}

}
