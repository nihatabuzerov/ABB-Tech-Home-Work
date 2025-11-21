package StrategyPattern;

public class PayPalPayment implements PaymentStrategy {

    @Override
    public void pay(int amount) {
        System.out.println(amount + " AZN PayPal ilə ödəndi");
    }
}

