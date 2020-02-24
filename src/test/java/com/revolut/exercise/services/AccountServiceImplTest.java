package com.revolut.exercise.services;

import com.revolut.exercise.TestDataFactory;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.exceptions.InsufficientBalanceException;
import com.revolut.exercise.models.Account;
import com.revolut.exercise.models.TransactionDetails;
import com.revolut.exercise.models.values.TransactionType;
import com.revolut.exercise.services.impl.AccountServiceImpl;
import com.revolut.exercise.services.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class AccountServiceImplTest {

    private AccountService accountService = AccountServiceImpl.getInstance();

    @Test
    public void shouldCreditGivenAmountToExistingAccount() throws EntityNotFoundException {
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountService.createOrUpdate(account);
        Assert.assertNotNull(createdAccount);
        Assert.assertEquals(createdAccount.getBalance().getAmount(), new BigDecimal(2000));
        TransactionServiceImpl.getInstance().deleteAll();
        accountService.credit(createdAccount.getId(), new BigDecimal(500));
        Assert.assertEquals(createdAccount.getBalance().getAmount(), new BigDecimal(2500));
        List<TransactionDetails> transactionDetails = accountService.getTransactionDetails(createdAccount.getId());
        Assert.assertTrue(transactionDetails.size() == 1);
        Assert.assertTrue(transactionDetails.get(0).getTransactionAmount().equals(new BigDecimal(500)));
        Assert.assertTrue(transactionDetails.get(0).getTransactionType().equals(TransactionType.CREDIT));
    }

    @Test
    public void shouldDebitGivenAmountToExistingAccount() throws Exception {
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountService.createOrUpdate(account);
        Assert.assertNotNull(createdAccount);
        Assert.assertEquals(createdAccount.getBalance().getAmount(), new BigDecimal(2000));
        accountService.debit(createdAccount.getId(), new BigDecimal(500));
        Assert.assertEquals(createdAccount.getBalance().getAmount(), new BigDecimal(1500));
        List<TransactionDetails> transactionDetails = accountService.getTransactionDetails(createdAccount.getId());
        Assert.assertTrue(transactionDetails.size() == 1);
        Assert.assertTrue(transactionDetails.get(0).getTransactionAmount().equals(new BigDecimal(500)));
        Assert.assertTrue(transactionDetails.get(0).getTransactionType().equals(TransactionType.DEBIT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentWhenNegativeAmountIsCreditedToAccount() throws Exception{
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountService.createOrUpdate(account);
        accountService.credit(createdAccount.getId(), new BigDecimal(-5000));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentWhenNegativeAmountIsDebitedToAccount() throws Exception{
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountService.createOrUpdate(account);
        accountService.debit(createdAccount.getId(), new BigDecimal(-5000));
    }

    @Test(expected = InsufficientBalanceException.class)
    public void shouldThrowIllegalArgumentWhenInsufficientBalanceToDebitedToAccount() throws Exception{
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountService.createOrUpdate(account);
        accountService.debit(createdAccount.getId(), new BigDecimal(5000));
    }
}
