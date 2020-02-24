package com.revolut.exercise.models;

import com.revolut.exercise.api.AbstractData;
import com.revolut.exercise.models.values.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDetails extends AbstractData {

    private long accountId;
    private BigDecimal transactionAmount;
    private TransactionType transactionType;
    private Date transactionTime;

    public TransactionDetails() {
    }

    public TransactionDetails(long accountId, BigDecimal transactionAmount, TransactionType transactionType, Date transactionTime) {
        this.accountId = accountId;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionTime = transactionTime;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }
}
