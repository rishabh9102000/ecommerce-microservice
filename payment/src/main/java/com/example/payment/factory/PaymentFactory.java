package com.example.payment.factory;

import com.example.payment.model.PaymentMode;
import com.example.payment.strategy.CreditCardPaymentStrategy;
import com.example.payment.strategy.PaymentStrategy;
import com.example.payment.strategy.UpiPaymentStrategy;
import com.example.payment.strategy.WalletPaymentStrategy;

public class PaymentFactory {
    public static PaymentStrategy getStrategy(PaymentMode mode){

        return switch (mode) {
            case CREDIT_CARD -> new CreditCardPaymentStrategy();
            case WALLET -> new WalletPaymentStrategy();
            case UPI -> new UpiPaymentStrategy();

        };
    }
}
