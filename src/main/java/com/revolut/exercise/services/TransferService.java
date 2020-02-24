package com.revolut.exercise.services;

import com.revolut.exercise.api.DataService;
import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.exceptions.InsufficientBalanceException;
import com.revolut.exercise.models.TransferDetails;


public interface TransferService extends DataService<TransferDetails> {

     TransferDetails transfer(final TransferDetails transferDetails) throws EntityNotFoundException, InsufficientBalanceException;
}
