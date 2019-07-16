package com.instanceofcake.revolut.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.instanceofcake.revolut.rest.BaseApiTest;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.exception.NoAccountDataFoundApiException;

public class AccountServiceTest extends BaseApiTest {

	AccountService service;

	@Before
	public void init() {
		service = new AccountService();
	}

	@Test
	public void testGetAllAccounts() {
		List<Account> accounts = service.getAllAccounts();
		assertNotNull(accounts);

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
