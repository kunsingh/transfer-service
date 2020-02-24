package com.revolut.exercise.services;

import com.revolut.exercise.api.DataService;
import com.revolut.exercise.models.TransactionDetails;

import java.util.List;

public interface TransactionService extends DataService<TransactionDetails> {

    List<TransactionDetails> findTransactionForGivenAccount(Long accountId);

}
