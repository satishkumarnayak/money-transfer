package com.instanceofcake.revolut.rest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dalesbred.query.SqlQuery;
import org.dalesbred.query.VariableResolver;

import com.instanceofcake.revolut.rest.database.DB;
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
	
	public Transfer findById(int id) {

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("id", id);
		SqlQuery query = SqlQuery.namedQuery("SELECT * FROM TRANSFER WHERE ID = :id", VariableResolver.forMap(params));

		Transfer transfer = DB.db.findOptional(Transfer.class, query).orElse(null);

		return transfer;
	}
	
	public Transfer findByFromAccountIdAndToAccountIdAndAmount(int fromAccountId, int toAccountId, int amount) {

		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("fromAccountId", fromAccountId);
		params.put("toAccountId", toAccountId);
		params.put("amount", amount);
		SqlQuery query = SqlQuery.namedQuery(
				"SELECT * FROM TRANSFER WHERE FROM_ACCOUNT_ID =:fromAccountId and TO_ACCOUNT_ID =:toAccountId and AMOUNT =:amount  ",
				VariableResolver.forMap(params));

		Transfer transfer = DB.db.findOptional(Transfer.class, query).orElse(null);

		return transfer;
	}

}
