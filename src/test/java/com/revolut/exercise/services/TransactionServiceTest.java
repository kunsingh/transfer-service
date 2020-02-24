package com.revolut.exercise.services;

import com.revolut.exercise.TestDataFactory;
import com.revolut.exercise.models.TransactionDetails;
import com.revolut.exercise.services.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TransactionServiceTest {

    private final TransactionService transactionService = TransactionServiceImpl.getInstance();

    @Test
    public void shouldCreateTransactionDetails() throws Exception{
        transactionService.createOrUpdate(TestDataFactory.getTransactionDetailsForCredit());
        transactionService.createOrUpdate(TestDataFactory.getTransactionDetailsForCredit());
        transactionService.createOrUpdate(TestDataFactory.getTransactionDetailsForDebit());
        transactionService.createOrUpdate(TestDataFactory.getTransactionDetailsForDebit());
        List<TransactionDetails> transactionDetails = transactionService.findTransactionForGivenAccount(TestDataFactory.getTransactionDetailsForCredit().getAccountId());
        Assert.assertTrue(transactionDetails.size() == 4);
        transactionService.deleteAll();

    }
}
