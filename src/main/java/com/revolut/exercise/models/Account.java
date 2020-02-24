package com.revolut.exercise.models;

import com.revolut.exercise.api.AbstractData;

public class Account extends AbstractData {

    private Balance balance;

    public Account() {
    }

    public void setBalance(final Balance balance) {
        this.balance = balance;
    }


    public Balance getBalance() {
        return balance;
    }
}
