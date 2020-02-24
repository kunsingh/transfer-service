package com.revolut.exercise.services.impl;

import com.revolut.exercise.api.AbstractDataService;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.exceptions.InsufficientBalanceException;
import com.revolut.exercise.models.Account;
import com.revolut.exercise.models.TransferDetails;
import com.revolut.exercise.repositories.TransferRepository;
import com.revolut.exercise.services.AccountService;
import com.revolut.exercise.services.TransferService;
import com.revolut.exercise.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TransferServiceImpl extends AbstractDataService<TransferDetails, TransferRepository> implements TransferService {

    private final Logger LOGGER = LoggerFactory.getLogger(TransferServiceImpl.class);

    private static final TransferService INSTANCE = new TransferServiceImpl(TransferRepository.getInstance());

    public static TransferService getInstance() {
        return INSTANCE;
    }

    private AccountService accountService = AccountServiceImpl.getInstance();

    private TransferServiceImpl(TransferRepository repository) {
        super(repository);
    }

    @Override
    public synchronized TransferDetails transfer(final TransferDetails transferDetails) throws EntityNotFoundException, InsufficientBalanceException {
        Assert.requireNonNull(transferDetails, "transferDetails");
        final long sourceId = transferDetails.getSourceAccountNumber();
        final long targetId = transferDetails.getTargetAccountNumber();
        LOGGER.info("Initiating transfer between accounts {} , {}", sourceId, targetId);
        accountService.debit(sourceId, transferDetails.getAmount());
        accountService.credit(targetId, transferDetails.getAmount());
        transferDetails.setSuccess(true);
        transferDetails.setTransactionNote("Transaction successfully completed!");
        LOGGER.info("Transfer completed from accounts {} to {} for INR {}", sourceId, targetId, transferDetails.getAmount());
        return createOrUpdate(transferDetails);
    }
}
