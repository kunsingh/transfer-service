package com.revolut.exercise.controller;

import com.revolut.exercise.exceptions.EntityNotFoundException;
import com.revolut.exercise.exceptions.ExceptionHandler;
import com.revolut.exercise.models.Account;
import com.revolut.exercise.services.AccountService;
import com.revolut.exercise.services.impl.AccountServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/accounts")
public class AccountController {

    private AccountService accountService = AccountServiceImpl.getInstance();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {
        return Response.ok(accountService.findAll()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountById(@PathParam("id") String id) {
        try {
            final Account account = accountService.find(Long.parseLong(id));
            return Response.ok(account).build();
        } catch (final EntityNotFoundException ex) {
            return ExceptionHandler.handle(ex);
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrUpdateAccount(final Account account) {
        try {
            final Account createdAccount = accountService.createOrUpdate(account);
            return Response.ok(createdAccount).build();
        } catch (final EntityNotFoundException ex) {
            return ExceptionHandler.handle(ex);
        }
    }


    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response DeleteById(@PathParam("id") String id) {
        try {
            accountService.delete(Long.valueOf(id));
            return Response.ok().build();
        } catch (final EntityNotFoundException ex) {
            return ExceptionHandler.handle(ex);
        }
    }
}
