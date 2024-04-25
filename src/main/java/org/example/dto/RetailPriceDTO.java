package org.example.dto;

import java.math.BigDecimal;

public class RetailPriceDTO {
    private BigDecimal amount;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private String currencyCode;

    @Override
    public String toString() {
        return "RetailPriceDTO{" +
                "amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
