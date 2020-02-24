package com.revolut.exercise;

import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.models.Account;
import com.revolut.exercise.models.Balance;
import com.revolut.exercise.models.TransactionDetails;
import com.revolut.exercise.models.TransferDetails;
import com.revolut.exercise.models.values.CurrencyType;
import com.revolut.exercise.models.values.TransactionType;
import com.revolut.exercise.repositories.AccountRepository;

import java.math.BigDecimal;
import java.util.Date;

public class TestDataFactory {

    public static Account getAccount() {
        final Account account = new Account();
        final Balance balance = new Balance();
        balance.setAmount(new BigDecimal(2000));
        balance.setCurrencyType(CurrencyType.DOLLAR);
        account.setBalance(balance);
        return account;
    }

    public static void initializeData() {
        AccountRepository accountRepository = AccountRepository.getInstance();
        try {
            accountRepository.deleteAll();
            accountRepository.createOrUpdate(getAccount());
            accountRepository.createOrUpdate(getAccount());
            accountRepository.createOrUpdate(getAccount());
            accountRepository.createOrUpdate(getAccount());
        }catch (EntityNotFoundException ex){
            ex.printStackTrace();
        }

    }

    public static void cleanup()  {
        AccountRepository accountRepository = AccountRepository.getInstance();
        accountRepository.deleteAll();

    }

    public static TransactionDetails getTransactionDetailsForDebit() {
        return new TransactionDetails(1L, new BigDecimal(500), TransactionType.DEBIT, new Date(System.currentTimeMillis()));
    }

    public static TransactionDetails getTransactionDetailsForCredit() {
        return new TransactionDetails(1L, new BigDecimal(500), TransactionType.CREDIT, new Date(System.currentTimeMillis()));
    }

    public static TransferDetails getTransferDetails() {
        final TransferDetails transferDetails = new TransferDetails(1l, 2l, new BigDecimal(500));
        transferDetails.setSuccess(true);
        transferDetails.setTransactionNote("Successfully transferred");
        return transferDetails;
    }

}
