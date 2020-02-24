package com.revolut.exercise.controllers;

import com.revolut.exercise.TestDataFactory;
import com.revolut.exercise.controller.AccountController;
import com.revolut.exercise.models.Account;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import javax.ws.rs.core.Application;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountControllerTest extends JerseyTest {


    @BeforeClass
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9998;
    }


    @Test
    public void shouldGetAllTheAccounts(){
        TestDataFactory.initializeData();
        given().when().get("/accounts").then().body("size()", is(4));
    }

    @Test
    public void shouldReturnStatusOkWhenCallGETForValidAccount() {
        TestDataFactory.initializeData();
        expect().statusCode(200).contentType(ContentType.JSON)
                .when().get("/accounts/1");
    }

    @Test
    public void shouldReturnStatusNoContentWhenCallGETForNonExistingAccount() {
        expect().statusCode(204)
                .when().get("/accounts/10");
    }

    @Test
    public void shouldBeAbleToCreateANewAccount() throws Exception{
        TestDataFactory.cleanup();
        final Account account = TestDataFactory.getAccount();
        given().contentType("application/json").body(account)
                .when()
                    .post("/accounts")
                .then()
                    .statusCode(200)
                    .body("id", equalTo(0))
                    .body("balance.amount", equalTo(2000));

    }

    @Test
    public void shouldReturnStatusOkWhenCallDeleteForValidAccount() {
        TestDataFactory.initializeData();
        expect().statusCode(200)
                .when().delete("/accounts/0");
    }

    @Override
    public Application configure() {
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AccountController.class);
    }


}