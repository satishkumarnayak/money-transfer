package com.instanceofcake.revolut.rest.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.instanceofcake.revolut.rest.BaseApiTest;
import com.instanceofcake.revolut.rest.domain.Transfer;

public class TransferDaoTest extends BaseApiTest {

	TransferDao dao;

	@Before
	public void init() throws Exception {
		dao = new TransferDao();
	}

	@Test
	public void testCreate() {

		Transfer transfer = new Transfer();
		transfer.setFromAccountId(321);
		transfer.setToAccountId(322);
		transfer.setAmount(100);
		transfer.setStatus("success");

		int noOfRecordCreated = dao.create(transfer);
		assertEquals(1, noOfRecordCreated);

		Transfer actual = dao.findByFromAccountIdAndToAccountIdAndAmount(321, 322, 100);

		assertNotNull(actual);
		assertEquals(transfer.getFromAccountId(), actual.getFromAccountId());
		assertEquals(transfer.getToAccountId(), actual.getToAccountId());
		assertEquals(transfer.getAmount(), actual.getAmount());
		assertEquals(transfer.getStatus(), actual.getStatus());

	}

	@Test
	public void testFindById() {
		Transfer transfer = new Transfer();
		transfer.setFromAccountId(321);
		transfer.setToAccountId(322);
		transfer.setAmount(200);
		transfer.setStatus("success");

		int noOfRecordCreated = dao.create(transfer);
		assertEquals(1, noOfRecordCreated);

		Transfer tansferFound = dao.findByFromAccountIdAndToAccountIdAndAmount(321, 322, 200);

		Integer transferId = tansferFound.getId();
		Transfer actual = dao.findById(transferId);
		assertEquals(transfer.getFromAccountId(), actual.getFromAccountId());
		assertEquals(transfer.getToAccountId(), actual.getToAccountId());
		assertEquals(transfer.getAmount(), actual.getAmount());
		assertEquals(transfer.getStatus(), actual.getStatus());

	}

	@Test
	public void testFindByFromAccountIdAndToAccountIdAndAmount() {
		Transfer transfer = new Transfer();
		transfer.setFromAccountId(321);
		transfer.setToAccountId(322);
		transfer.setAmount(250);
		transfer.setStatus("success");

		int noOfRecordCreated = dao.create(transfer);
		assertEquals(1, noOfRecordCreated);

		Transfer actual = dao.findByFromAccountIdAndToAccountIdAndAmount(321, 322, 250);
		assertEquals(transfer.getFromAccountId(), actual.getFromAccountId());
		assertEquals(transfer.getToAccountId(), actual.getToAccountId());
		assertEquals(transfer.getAmount(), actual.getAmount());
		assertEquals(transfer.getStatus(), actual.getStatus());

	}

}
