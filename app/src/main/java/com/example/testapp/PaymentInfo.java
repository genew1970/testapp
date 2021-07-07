package com.example.testapp;

public class PaymentInfo {
    String merchantId;
    String terminalId;
    String amount;

    public PaymentInfo(String merchantId, String terminalId, String amount) {
        this.merchantId = merchantId;
        this.terminalId = terminalId;
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
