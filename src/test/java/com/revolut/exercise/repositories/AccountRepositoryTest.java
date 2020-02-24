package com.revolut.exercise.repositories;


import com.revolut.exercise.TestDataFactory;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.models.Account;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountRepositoryTest{

    private AccountRepository accountRepository = AccountRepository.getInstance();

    @Test
    public void shouldBeAbleToCreateANewAccountWhenValidAccountDetailsAreGiven() throws Exception{
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountRepository.createOrUpdate(account);
        Assert.assertNotNull(createdAccount);
        Assert.assertNotNull(createdAccount.getId());
    }

    @Test
    public void shouldBeAbleToUpdateExistingAccountWhenValidAccountDetailsAreGiven() throws Exception{
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountRepository.createOrUpdate(account);
        Assert.assertNotNull(createdAccount);
        Assert.assertEquals(createdAccount.getBalance().getAmount(), new BigDecimal(2000));
        createdAccount.getBalance().setAmount(new BigDecimal(5000));
        final Account updatedAccount = accountRepository.createOrUpdate(createdAccount);
        Assert.assertEquals(updatedAccount.getBalance().getAmount(), new BigDecimal(5000));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentWhenNullObjectIsPassedWhileCreatingAccount() throws Exception{
        accountRepository.createOrUpdate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentWhenNegativeAmountIsPassedWhileCreatingAccount() throws Exception{
        final Account account = TestDataFactory.getAccount();
        account.getBalance().setAmount(new BigDecimal(-5000));
        accountRepository.createOrUpdate(account);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenUpdatingNonExistingAccount() throws Exception{
        final Account account = TestDataFactory.getAccount();
        account.setId(10l);
        accountRepository.createOrUpdate(account);
    }

    @Test
    public void shouldBeAbleToFindAccountForCreatedId() throws Exception{
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountRepository.createOrUpdate(account);
        final Account accountObj = accountRepository.find(createdAccount.getId());
        Assert.assertNotNull(accountObj);
        Assert.assertEquals(accountObj.getId(), createdAccount.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenWhenFindingNonExistingAccount() throws Exception{
        accountRepository.find(10l);
    }

    @Test
    public void shouldBeAbleToDeleteAccountForGivenId() throws Exception{
        final Account account = TestDataFactory.getAccount();
        final Account createdAccount = accountRepository.createOrUpdate(account);
        accountRepository.delete(createdAccount.getId());
        try {
            accountRepository.find(createdAccount.getId());
            Assert.fail();
        }catch (EntityNotFoundException ex){
            Assert.assertTrue(true);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenWhenDeletingNonExistingAccount() throws Exception{
        accountRepository.delete(10l);
    }

}
