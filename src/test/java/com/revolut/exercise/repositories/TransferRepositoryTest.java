package com.revolut.exercise.repositories;


import com.revolut.exercise.TestDataFactory;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.models.TransferDetails;
import org.junit.Assert;
import org.junit.Test;


public class TransferRepositoryTest {

    private TransferRepository transferRepository = TransferRepository.getInstance();

    @Test
    public void shouldBeAbleToCreateANewTransferDetailsWhenValidDetailsAreGiven() throws Exception{
        final TransferDetails transferDetails = TestDataFactory.getTransferDetails();
        final TransferDetails createdTransferDetails = transferRepository.createOrUpdate(transferDetails);
        Assert.assertNotNull(createdTransferDetails);
        Assert.assertNotNull(createdTransferDetails.getId());
    }

    @Test
    public void shouldBeAbleToUpdateExistingTransferDetailsWhenValidTransferDetailsDetailsAreGiven() throws Exception{
        final TransferDetails TransferDetails = TestDataFactory.getTransferDetails();
        final TransferDetails createdTransferDetails = transferRepository.createOrUpdate(TransferDetails);
        Assert.assertNotNull(createdTransferDetails);
        Assert.assertTrue(createdTransferDetails.getSuccess());
        createdTransferDetails.setSuccess(false);
        final TransferDetails updatedTransferDetails = transferRepository.createOrUpdate(createdTransferDetails);
        Assert.assertFalse(updatedTransferDetails.getSuccess());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentWhenNullObjectIsPassedWhileCreatingTransferDetails() throws Exception{
        transferRepository.createOrUpdate(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenUpdatingNonExistingTransferDetails() throws Exception{
        final TransferDetails TransferDetails = TestDataFactory.getTransferDetails();
        TransferDetails.setId(10l);
        transferRepository.createOrUpdate(TransferDetails);
    }

    @Test
    public void shouldBeAbleToFindTransferDetailsForCreatedId() throws Exception{
        final TransferDetails TransferDetails = TestDataFactory.getTransferDetails();
        final TransferDetails createdTransferDetails = transferRepository.createOrUpdate(TransferDetails);
        final TransferDetails TransferDetailsObj = transferRepository.find(createdTransferDetails.getId());
        Assert.assertNotNull(TransferDetailsObj);
        Assert.assertEquals(TransferDetailsObj.getId(), createdTransferDetails.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenWhenFindingNonExistingTransferDetails() throws Exception{
        transferRepository.find(10l);
    }

    @Test
    public void shouldBeAbleToDeleteTransferDetailsForGivenId() throws Exception{
        final TransferDetails TransferDetails = TestDataFactory.getTransferDetails();
        final TransferDetails createdTransferDetails = transferRepository.createOrUpdate(TransferDetails);
        transferRepository.delete(createdTransferDetails.getId());
        try {
            transferRepository.find(createdTransferDetails.getId());
            Assert.fail();
        }catch (EntityNotFoundException ex){
            Assert.assertTrue(true);
        }
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowIEntityNotFoundWhenWhenDeletingNonExistingTransferDetails() throws Exception{
        transferRepository.delete(10l);
    }

}
