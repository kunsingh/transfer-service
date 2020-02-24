package com.revolut.exercise.repositories;


import com.revolut.exercise.TestDataFactory;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.models.TransactionDetails;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


public class TransactionRepositoryTest {

    private TransactionRepository transactionRepository = TransactionRepository.getInstance();

    @Test
    public void shouldBeAbleToCreateANewTransactionDetailsWhenValidDetailsAreGiven() throws Exception{
        final TransactionDetails createdTransactionDetails = transactionRepository.createOrUpdate(TestDataFactory.getTransactionDetailsForDebit());
        Assert.assertNotNull(createdTransactionDetails);
        Assert.assertNotNull(createdTransactionDetails.getId());
    }

    @Test
    public void shouldBeAbleToUpdateExistingTransactionDetailsWhenValidTransactionDetailsDetailsAreGiven() throws Exception{
        final TransactionDetails createdTransactionDetails = transactionRepository.createOrUpdate(TestDataFactory.getTransactionDetailsForCredit());
        Assert.assertNotNull(createdTransactionDetails);
        createdTransactionDetails.setTransactionAmount(new BigDecimal(1000));
        final TransactionDetails updatedTransactionDetails = transactionRepository.createOrUpdate(createdTransactionDetails);
        Assert.assertTrue(updatedTransactionDetails.getTransactionAmount().equals(new BigDecimal(1000)));
        transactionRepository.deleteAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentWhenNullObjectIsPassedWhileCreatingTransactionDetails() throws Exception{
        transactionRepository.createOrUpdate(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenUpdatingNonExistingTransactionDetails() throws Exception{
        final TransactionDetails transactionDetails = TestDataFactory.getTransactionDetailsForDebit();
        transactionDetails.setId(10l);
        transactionRepository.createOrUpdate(transactionDetails);
        transactionRepository.deleteAll();
    }

    @Test
    public void shouldBeAbleToFindTransactionDetailsForCreatedId() throws Exception{
        final TransactionDetails createdTransactionDetails = transactionRepository.createOrUpdate(TestDataFactory.getTransactionDetailsForDebit());
        final TransactionDetails transactionDetailsObj = transactionRepository.find(createdTransactionDetails.getId());
        Assert.assertNotNull(transactionDetailsObj);
        Assert.assertEquals(transactionDetailsObj.getId(), createdTransactionDetails.getId());
        transactionRepository.deleteAll();
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenWhenFindingNonExistingTransactionDetails() throws Exception{
        transactionRepository.find(10l);
    }

    @Test
    public void shouldBeAbleToDeleteTransactionDetailsForGivenId() throws Exception{
        final TransactionDetails createdTransactionDetails = transactionRepository.createOrUpdate(TestDataFactory.getTransactionDetailsForCredit());
        transactionRepository.delete(createdTransactionDetails.getId());
        try {
            transactionRepository.find(createdTransactionDetails.getId());
            Assert.fail();
        }catch (EntityNotFoundException ex){
            Assert.assertTrue(true);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenWhenDeletingNonExistingTransactionDetails() throws Exception{
        transactionRepository.delete(10l);
    }

}
