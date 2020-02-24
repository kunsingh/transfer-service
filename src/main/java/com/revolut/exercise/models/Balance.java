package com.revolut.exercise.models;

import com.revolut.exercise.models.values.CurrencyType;

import java.math.BigDecimal;

public class Balance {

    private BigDecimal amount;
    private CurrencyType currencyType;

    public Balance(){
        currencyType = CurrencyType.DOLLAR;
    }

    public Balance(BigDecimal amount, CurrencyType currencyType) {
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void addAmount(final BigDecimal amount){
        setAmount(this.amount.add(amount));
    }

    public void subtractAmount(final BigDecimal amount){
        setAmount(this.amount.subtract(amount));
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal(1000);
        BigDecimal amount1 = new BigDecimal(500);
        System.out.println(amount.subtract(amount1));

    }
}
