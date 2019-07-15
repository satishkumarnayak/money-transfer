package com.instanceofcake.revolut.rest.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.instanceofcake.revolut.rest.BaseApiTest;
import com.instanceofcake.revolut.rest.domain.Account;

public class AccountDaoTest extends BaseApiTest {

	AccountDao dao;

	@Before
	public void init() {
		dao = new AccountDao();
	}

	@Test
	public void testFindAll() {
		List<Account> accounts = dao.findAll();
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

		Account account3 = accounts.get(2);
		assertNotNull(account3);
		assertEquals(Integer.valueOf(323), account3.getId());
		assertEquals(Integer.valueOf(600), account3.getBalance());
		assertEquals("CHECKING", account3.getType());

	}

	@Test
	public void testFindById() {
		Account account = dao.findById(323);
		assertNotNull(account);
		assertEquals(Integer.valueOf(323), account.getId());
		assertEquals(Integer.valueOf(600), account.getBalance());
		assertEquals("CHECKING", account.getType());

	}

	@Test
	public void testUpdate() {

		Account account = dao.findById(322);
		assertNotNull(account);
		assertEquals(Integer.valueOf(900), account.getBalance());
		account.setBalance(300);
		dao.update(account);
		Account updatedAccount = dao.findById(322);
		assertNotNull(updatedAccount);
		assertEquals(Integer.valueOf(300), updatedAccount.getBalance());

	}
}
