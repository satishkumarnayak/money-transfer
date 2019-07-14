package com.instanceofcake.revolut.rest.service;

import java.util.List;

import com.instanceofcake.revolut.rest.dao.AccountDao;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.exception.NoAccountDataFoundApiException;

public class AccountService {

	AccountDao dao = new AccountDao();

	public List<Account> getAllAccounts() {
		List<Account> accounts = dao.findAll();
		if (accounts == null || accounts.isEmpty())
			throw new NoAccountDataFoundApiException(404, "No Data Found", "No Accounts Data Found");
		return accounts;
	}

}
