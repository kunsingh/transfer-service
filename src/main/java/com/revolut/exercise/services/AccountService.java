package com.revolut.exercise.services;

import com.revolut.exercise.api.DataService;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.exceptions.InsufficientBalanceException;
import com.revolut.exercise.models.Account;
import com.revolut.exercise.models.TransactionDetails;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService extends DataService<Account> {

    Account debit(Long id, BigDecimal amount) throws EntityNotFoundException, InsufficientBalanceException;

    Account credit(Long id, BigDecimal amount) throws EntityNotFoundException;

    List<TransactionDetails> getTransactionDetails(Long accountId);

}
