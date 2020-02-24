package com.revolut.exercise.controllers;

import com.revolut.exercise.TestDataFactory;
import com.revolut.exercise.controller.TransferController;
import com.revolut.exercise.models.TransferDetails;
import com.revolut.exercise.repositories.AccountRepository;
import io.restassured.RestAssured;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.ws.rs.core.Application;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransferControllerTest extends JerseyTest {

    final AccountRepository accountRepository = AccountRepository.getInstance();

    @BeforeClass
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9998;
    }

    @Test
    public void shouldBeAbleToTransferMoneyBetweenAccountsSuccessfully(){
        TestDataFactory.initializeData();
        final TransferDetails transferDetails = new TransferDetails(0l, 1l, new BigDecimal(500));
        given().contentType("application/json").body(transferDetails)
                .when()
                    .post("/transfer")
                .then()
                    .statusCode(200)
                    .body("sourceAccountNumber", equalTo(0))
                    .body("targetAccountNumber", equalTo(1))
                    .body("amount", equalTo(500));
        TestDataFactory.cleanup();
    }

    @Override
    public Application configure() {
        return new ResourceConfig(TransferController.class);
    }

    public void initializeData() throws Exception{
        accountRepository.createOrUpdate(TestDataFactory.getAccount());
        accountRepository.createOrUpdate(TestDataFactory.getAccount());
        accountRepository.createOrUpdate(TestDataFactory.getAccount());
        accountRepository.createOrUpdate(TestDataFactory.getAccount());
    }

}