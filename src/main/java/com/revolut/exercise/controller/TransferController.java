package com.revolut.exercise.controller;


import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.exceptions.ExceptionHandler;
import com.revolut.exercise.exceptions.InsufficientBalanceException;
import com.revolut.exercise.models.TransferDetails;
import com.revolut.exercise.services.TransferService;
import com.revolut.exercise.services.impl.TransferServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transfer")
public class TransferController {

    private TransferService transferService = TransferServiceImpl.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(final TransferDetails details)  {
        try {
            final TransferDetails transferDetails = transferService.transfer(details);
            return Response.ok(transferDetails).build();
        }catch (final EntityNotFoundException | InsufficientBalanceException ex){
            return ExceptionHandler.handle(ex);
        }
    }

}
