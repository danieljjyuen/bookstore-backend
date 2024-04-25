package org.example.dto;

import java.math.BigDecimal;

public class ListPriceDTO {
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    private BigDecimal amount;
    private String currencyCode;

    @Override
    public String toString() {
        return "ListPriceDTO{" +
                "amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
