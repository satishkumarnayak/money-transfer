package com.instanceofcake.revolut.rest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dalesbred.query.SqlQuery;
import org.dalesbred.query.VariableResolver;

import com.instanceofcake.revolut.rest.database.DB;
import com.instanceofcake.revolut.rest.domain.Account;

public class AccountDao {

	public List<Account> findAll() {

		SqlQuery query = SqlQuery.namedQuery("SELECT * FROM ACCOUNT", Account.class);

		List<Account> accounts = DB.db.findAll(Account.class, "SELECT * FROM ACCOUNT");

		return accounts;

	}

	public Account findById(int id) {

		Map<String, Integer> params = new HashMap();
		params.put("id", id);
		SqlQuery query = SqlQuery.namedQuery("SELECT * FROM ACCOUNT WHERE ID = :id", VariableResolver.forMap(params));

		Account account = DB.db.findOptional(Account.class, query).orElse(null);

		return account;
	}

	public int update(Account account) {
		Map<String, Integer> params = new HashMap();
		params.put("id", account.getId());
		params.put("balance", account.getBalance());
		SqlQuery query = SqlQuery.namedQuery("UPDATE ACCOUNT SET BALANCE = :balance WHERE ID = :id",
				VariableResolver.forMap(params));

		int noOfRecordsUpdated = DB.db.update(query);

		return noOfRecordsUpdated;

	}

}
