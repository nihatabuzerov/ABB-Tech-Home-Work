package StrategyPattern;

public class Main {
    public static void main(String[] args) {

        PaymentStrategy creditCard = new CreditCardPayment();
        PaymentStrategy paypal = new PayPalPayment();

        ShoppingCart cart1 = new ShoppingCart(creditCard);
        cart1.checkout(100);

        ShoppingCart cart2 = new ShoppingCart(paypal);
        cart2.checkout(200);
    }
}

