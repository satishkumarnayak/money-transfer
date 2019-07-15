package com.instanceofcake.revolut.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.instanceofcake.revolut.rest.BaseApiTest;
import com.instanceofcake.revolut.rest.dao.AccountDao;
import com.instanceofcake.revolut.rest.dao.TransferDao;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.domain.Transfer;
import com.instanceofcake.revolut.rest.exception.InvalidAmountApiException;
import com.instanceofcake.revolut.rest.exception.NoAccountDataFoundApiException;
import com.instanceofcake.revolut.rest.exception.InsufficientFundsApiException;
import com.instanceofcake.revolut.rest.exception.ToandFromSameAccountApiException;

public class TransferServiceTest extends BaseApiTest {

	TransferService serviceDao;
	TransferDao transferDao;
	AccountDao accountDao;

	@Before
	public void init() {
		serviceDao = new TransferService();
		transferDao = new TransferDao();
		accountDao = new AccountDao();
	}

	@Test
	public void testSuccessWhenValidBodyGiven() {
		Transfer transfer = new Transfer();
		int transferAmount = 20;
		transfer.setAmount(transferAmount);
		transfer.setFromAccountId(321);
		transfer.setToAccountId(322);
		transfer.setStatus("initiated");
		Account fromAccountBeforeTransfer = accountDao.findById(321);
		Account toAccountBeforeTransfer = accountDao.findById(322);
		int noOfRecord = serviceDao.transfer(transfer);
		assertEquals(1, noOfRecord);
		Transfer transferActual = transferDao.findByFromAccountIdAndToAccountIdAndAmount(321, 322, transferAmount);
		assertNotNull(transferActual);
		Account fromAccountAfterTansfer = accountDao.findById(321);
		Account toAccountAfterTransfer = accountDao.findById(322);

		assertEquals(Integer.valueOf(fromAccountBeforeTransfer.getBalance() - transferAmount),
				fromAccountAfterTansfer.getBalance());
		assertEquals(Integer.valueOf(toAccountBeforeTransfer.getBalance() + transferAmount),
				toAccountAfterTransfer.getBalance());

	}

	@Test
	public void testInvalidAmountApiException_whenNegativeAmountGiven() {
		Transfer transfer = new Transfer();
		transfer.setAmount(-1);
		transfer.setFromAccountId(321);
		transfer.setToAccountId(322);
		transfer.setStatus("initiated");
		Account fromAccountBeforeTransfer = accountDao.findById(321);
		Account toAccountBeforeTransfer = accountDao.findById(322);
		try {
			int noOfRecord = serviceDao.transfer(transfer);
			assertEquals(0, noOfRecord);
		} catch (InvalidAmountApiException e) {
			assertEquals("Amount cannot be negative.", e.getDescription());
		} finally {
			assertDataConsistency(fromAccountBeforeTransfer, toAccountBeforeTransfer, 321, 322, -1);
		}

	}

	@Test
	public void testToandFromSameAccountApiException_whenSameAccountGiven() {
		Transfer transfer = new Transfer();
		transfer.setAmount(10);
		transfer.setFromAccountId(321);
		transfer.setToAccountId(321);
		transfer.setStatus("initiated");
		Account fromAccountBeforeTransfer = accountDao.findById(321);
		Account toAccountBeforeTransfer = accountDao.findById(321);
		try {
			int noOfRecord = serviceDao.transfer(transfer);
			assertEquals(0, noOfRecord);
		} catch (ToandFromSameAccountApiException e) {
			assertEquals("To  Account and From Account Id cannot be same.", e.getDescription());
		} finally {
			assertDataConsistency(fromAccountBeforeTransfer, toAccountBeforeTransfer, 321, 321, 0);
		}

	}

	@Test
	public void testNoAccountDataFoundApiException_whenNonExistenceFromAccountIdGiven() {
		Transfer transfer = new Transfer();
		transfer.setAmount(10);
		transfer.setFromAccountId(5851);
		transfer.setToAccountId(321);
		transfer.setStatus("initiated");
		Account toAccountBeforeTransfer = accountDao.findById(321);
		try {
			int noOfRecord = serviceDao.transfer(transfer);
			assertEquals(0, noOfRecord);
		} catch (NoAccountDataFoundApiException e) {
			assertEquals("From Account cannot be found with Account Id-5851", e.getDescription());
		} finally {
			assertDataConsistency(toAccountBeforeTransfer, 321);
		}

	}

	@Test
	public void testNoAccountDataFoundApiException_whenNonExistenceToAccountIdGiven() {
		Transfer transfer = new Transfer();
		transfer.setAmount(10);
		transfer.setFromAccountId(321);
		transfer.setToAccountId(85857);
		transfer.setStatus("initiated");
		Account fromAccountBeforeTransfer = accountDao.findById(321);
		try {
			int noOfRecord = serviceDao.transfer(transfer);
			assertEquals(0, noOfRecord);
		} catch (NoAccountDataFoundApiException e) {
			assertEquals("To  Account cannot be found with Account Id-85857", e.getDescription());
		} finally {
			assertDataConsistency(fromAccountBeforeTransfer, 321);
		}

	}

	@Test
	public void testNotEnoughBalanceAmountApiException_whenInsufficientFundGiven() {
		Transfer transfer = new Transfer();
		transfer.setAmount(9999);
		transfer.setFromAccountId(321);
		transfer.setToAccountId(322);
		transfer.setStatus("initiated");
		Account fromAccountBeforeTransfer = accountDao.findById(321);
		Account toAccountBeforeTransfer = accountDao.findById(322);
		try {
			int noOfRecord = serviceDao.transfer(transfer);
			assertEquals(0, noOfRecord);
		} catch (InsufficientFundsApiException e) {
			assertEquals("Insufficient funds for transfer with Account Id-321", e.getDescription());
		} finally {
			assertDataConsistency(fromAccountBeforeTransfer, toAccountBeforeTransfer, 321, 322, 0);
		}

	}

	private void assertDataConsistency(Account accountBeforeTransfer, Integer accountId) {

		Account toAccountAfterTransfer = accountDao.findById(accountId);

		assertEquals(accountBeforeTransfer.getBalance(), Integer.valueOf(toAccountAfterTransfer.getBalance()));

	}

	private void assertDataConsistency(Account fromAccountBeforeTransfer, Account toAccountBeforeTransfer,
			Integer fromAccountId, Integer toAccountId, Integer transferAmount) {
		if (transferAmount < 0) {
			transferAmount = 0;
		}
		Account fromAccountAfterTansfer = accountDao.findById(fromAccountId);
		Account toAccountAfterTransfer = accountDao.findById(toAccountId);
		assertEquals(fromAccountBeforeTransfer.getBalance(),
				Integer.valueOf(fromAccountAfterTansfer.getBalance() - transferAmount));
		assertEquals(toAccountBeforeTransfer.getBalance(),
				Integer.valueOf(toAccountAfterTransfer.getBalance() + transferAmount));

	}
}
