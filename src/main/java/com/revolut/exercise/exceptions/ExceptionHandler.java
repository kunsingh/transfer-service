package com.revolut.exercise.exceptions;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.core.Response;

public class ExceptionHandler {

    public static Response handle(final Exception exception){
        if(exception instanceof EntityNotFoundException){
            return Response
                    .status(HttpStatus.NO_CONTENT_204, exception.getMessage())
                    .entity(exception.getMessage())
                    .type("text/plain")
                    .build();
        }

        if(exception instanceof InsufficientBalanceException){
            return Response
                    .status(HttpStatus.CONFLICT_409, exception.getMessage())
                    .entity(exception.getMessage())
                    .type("text/plain")
                    .build();
        }
        return Response.ok().build();
    }
}
