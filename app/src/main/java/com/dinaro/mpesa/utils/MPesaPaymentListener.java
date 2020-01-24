package com.dinaro.mpesa.utils;

public interface MPesaPaymentListener {

    void onStkpushSuccess(String transactionId);
    void onStkpushFailure();

}
