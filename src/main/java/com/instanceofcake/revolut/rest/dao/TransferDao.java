package com.instanceofcake.revolut.rest.dao;

import java.util.List;

import org.dalesbred.query.SqlQuery;

import com.instanceofcake.revolut.rest.database.DB;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.domain.Transfer;

public class TransferDao {

	AccountDao accountDao = new AccountDao();

	public int create(Transfer transfer) {

		SqlQuery namedQuery = SqlQuery
				.namedQuery("INSERT INTO TRANSFER ( FROM_ACCOUNT_ID, TO_ACCOUNT_ID, AMOUNT, STATUS)"
						+ " VALUES (:fromAccountId, :toAccountId, :amount, :status)", transfer);
		return DB.db.update(namedQuery);
	}

	public int update(Transfer transfer) {

		SqlQuery namedQuery = SqlQuery.namedQuery("UPDATE TRANSFER SET (STATUS)" + " VALUES (:status)", transfer);
		return DB.db.update(namedQuery);
	}

	public List<Transfer> findAll() {

		List<Transfer> transfers = DB.db.findAll(Transfer.class, "SELECT * FROM TRANSFER");

		return transfers;

	}

}
