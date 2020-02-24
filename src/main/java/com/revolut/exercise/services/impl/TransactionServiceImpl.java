package com.revolut.exercise.services.impl;

import com.revolut.exercise.api.AbstractDataService;
import com.revolut.exercise.models.TransactionDetails;
import com.revolut.exercise.repositories.TransactionRepository;
import com.revolut.exercise.services.TransactionService;
import com.revolut.exercise.utils.Assert;

import java.util.List;
import java.util.stream.Collectors;


public class TransactionServiceImpl extends AbstractDataService<TransactionDetails, TransactionRepository> implements TransactionService {

    private static final TransactionService INSTANCE = new TransactionServiceImpl(TransactionRepository.getInstance());

    public static TransactionService getInstance() {
        return INSTANCE;
    }

    private TransactionServiceImpl(TransactionRepository repository) {
        super(repository);
    }

    @Override
    public List<TransactionDetails> findTransactionForGivenAccount(final Long accountId) {
        Assert.requireNonNull(accountId, "accountId");
        final List<TransactionDetails> transactions = getRepository().findAll();
        return transactions.stream().filter(transaction -> (transaction.getAccountId() == accountId)).collect(Collectors.toList());
    }
}
