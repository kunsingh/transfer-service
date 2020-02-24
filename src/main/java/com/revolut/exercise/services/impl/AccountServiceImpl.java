package com.revolut.exercise.services.impl;

import com.revolut.exercise.api.AbstractDataService;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.exceptions.InsufficientBalanceException;
import com.revolut.exercise.models.Account;
import com.revolut.exercise.models.TransactionDetails;
import com.revolut.exercise.models.values.TransactionType;
import com.revolut.exercise.repositories.AccountRepository;
import com.revolut.exercise.services.AccountService;
import com.revolut.exercise.services.TransactionService;
import com.revolut.exercise.utils.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class AccountServiceImpl extends AbstractDataService<Account, AccountRepository> implements AccountService {

    private static final AccountService INSTANCE = new AccountServiceImpl(AccountRepository.getInstance());

    private final TransactionService transactionService = TransactionServiceImpl.getInstance();

    public static AccountService getInstance() {
        return INSTANCE;
    }

    private AccountServiceImpl(AccountRepository repository) {
        super(repository);
    }


    @Override
    public synchronized Account credit(Long id, BigDecimal amount) throws EntityNotFoundException {
        Assert.requireNonNull(id, "account id");
        if (Objects.isNull(amount) || BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new IllegalArgumentException("You can only credit positive amount.");
        }
        final Account account = getRepository().find(id);
        account.getBalance().addAmount(amount);
        final Account updatedAccount = getRepository().createOrUpdate(account);
        final TransactionDetails transactionDetails = new TransactionDetails(id, amount, TransactionType.CREDIT, new Date(System.currentTimeMillis()));
        transactionService.createOrUpdate(transactionDetails);
        return updatedAccount;
    }


    @Override
    public synchronized Account debit(Long id, BigDecimal amount) throws EntityNotFoundException, InsufficientBalanceException {
        Assert.requireNonNull(id, "account id");
        if (Objects.isNull(amount) || BigDecimal.ZERO.compareTo(amount) > 0) {
            throw new IllegalArgumentException("You can only debit positive amount for account id: " + id);
        }
        final Account account = getRepository().find(id);
        if (account.getBalance().getAmount().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient funds on the account id: " + id);
        }
        account.getBalance().subtractAmount(amount);
        final Account updatedAccount = getRepository().createOrUpdate(account);
        final TransactionDetails transactionDetails = new TransactionDetails(id, amount, TransactionType.DEBIT, new Date(System.currentTimeMillis()));
        transactionService.createOrUpdate(transactionDetails);
        return updatedAccount;
    }

    @Override
    public List<TransactionDetails> getTransactionDetails(final Long accountId) {
        return transactionService.findTransactionForGivenAccount(accountId);
    }



}
