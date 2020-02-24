package com.revolut.exercise.models;

import com.revolut.exercise.api.AbstractData;
import com.revolut.exercise.utils.Assert;

import java.math.BigDecimal;

public class TransferDetails extends AbstractData {

    private Long sourceAccountNumber;
    private Long targetAccountNumber;
    private BigDecimal amount;
    private Boolean success;
    private String transactionNote;

    public TransferDetails() {
    }

    public TransferDetails(final Long sourceAccountNumber, final Long targetAccountNumber, final BigDecimal amount) {
        Assert.requireNonNull(sourceAccountNumber, "sourceAccountNumber");
        Assert.requireNonNull(targetAccountNumber, "targetAccountNumber");
        Assert.requireNonNull(amount, "amount");
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.amount = amount;
    }

    public Long getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public Long getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Boolean getSuccess() {
        return success;
    }


    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTransactionNote() {
        return transactionNote;
    }

    public void setTransactionNote(String transactionNote) {
        this.transactionNote = transactionNote;
    }

    public void setSourceAccountNumber(Long sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public void setTargetAccountNumber(Long targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
