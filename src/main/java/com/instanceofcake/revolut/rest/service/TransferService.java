package com.instanceofcake.revolut.rest.service;

import java.util.List;
import org.dalesbred.transaction.Isolation;
import org.dalesbred.transaction.Propagation;

import com.instanceofcake.revolut.rest.dao.AccountDao;
import com.instanceofcake.revolut.rest.dao.TransferDao;
import com.instanceofcake.revolut.rest.database.DB;
import com.instanceofcake.revolut.rest.domain.Account;
import com.instanceofcake.revolut.rest.domain.Transfer;
import com.instanceofcake.revolut.rest.exception.InvalidAmountApiException;
import com.instanceofcake.revolut.rest.exception.NoAccountDataFoundApiException;
import com.instanceofcake.revolut.rest.exception.NoTransferDataFoundApiException;
import com.instanceofcake.revolut.rest.exception.NotEnoughBalanceAmountApiException;
import com.instanceofcake.revolut.rest.exception.ToandFromSameAccountApiException;

public class TransferService {

	TransferDao transferDao = new TransferDao();
	AccountDao accountDao = new AccountDao();

	public List<Transfer> findAll() {
		List<Transfer> transfers = transferDao.findAll();
		if (transfers == null || transfers.isEmpty()) {
			throw new NoTransferDataFoundApiException(404, "No Data Found", "No Transfer Data Found");
		}
		return transfers;
	}

	public int transfer(Transfer transfer) {

		if (transfer.getAmount() < 0) {
			throw new InvalidAmountApiException(412, "Precondition Failed", "Amount cannot be negative.");
		}
		if (transfer.getFromAccountId().equals(transfer.getToAccountId())) {
			throw new ToandFromSameAccountApiException(412, "Precondition Failed", "To  Account and From Account Id cannot be same.");
		}
		Account fromAccount = accountDao.findById(transfer.getFromAccountId());
		if (fromAccount == null) {
			throw new NoAccountDataFoundApiException(404, "No Data Found",
					"From Account cannot be found with Account Id-" + transfer.getFromAccountId());
		}
		Account toAccount = accountDao.findById(transfer.getToAccountId());
		if (toAccount == null) {
			throw new NoAccountDataFoundApiException(404, "No Data Found",
					"To  Account cannot be found with Account Id-" + transfer.getToAccountId());
		}
		if (fromAccount.getBalance() - transfer.getAmount() < 0) {
			throw new NotEnoughBalanceAmountApiException(412, "Precondition Failed",
					"Insufficient funds for transfer with Account Id-" + transfer.getFromAccountId());
		}

		final int fromAccountBalanceBeforeTransfer = fromAccount.getBalance();
		final int toAccountBalanceBeforeTransfer = toAccount.getBalance();

		DB.db.withVoidTransaction(Propagation.NESTED,Isolation.READ_COMMITTED, tx -> {

			try {
				fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmount());
				accountDao.update(fromAccount);
				toAccount.setBalance(toAccount.getBalance() + transfer.getAmount());
				accountDao.update(toAccount);
			} catch (Exception e) {
				transfer.setStatus("failure");
				transferDao.create(transfer);
				revertTransfer(fromAccount, toAccount, fromAccountBalanceBeforeTransfer,toAccountBalanceBeforeTransfer);
				try {
					throw new Exception("Transaction Failed.");
				} catch (Exception e1) {
				}
			}
		});

		transfer.setStatus("Transfer completed successfully");
		return transferDao.create(transfer);

	}

	private void revertTransfer(Account fromAccount, Account toAccount, final int fromAccountBalanceBeforeTransfer,
			final int toAccountBalanceBeforeTransfer) {
		fromAccount.setBalance(fromAccountBalanceBeforeTransfer);
		accountDao.update(fromAccount);
		toAccount.setBalance(toAccountBalanceBeforeTransfer);
		accountDao.update(toAccount);
	}

}
