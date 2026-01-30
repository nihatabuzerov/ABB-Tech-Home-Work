package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

@SuppressWarnings({ "ExplicitToImplicitClassMigration", "RedundantSuppression" })
public class Main {

    static void main () {

        Scanner scanner = new Scanner(System.in);
        int orderCount;
        System.out.print("How many pizzas are you going to order? ");
        orderCount = scanner.nextInt();

        System.out.println();

        Order[] orders = new Order[orderCount];

        for (int i = 0; i < orderCount; i++) {
            orders[i] = new Order();
            orders[i].setOrderNumber(i + 1);
        }

        long startTime = System.currentTimeMillis();

        for (Order order : orders) {
            order.start();
        }

        for (Order order : orders) {
            try {
                order.join();
            }
            catch (InterruptedException e) {
               throw new RuntimeException(e);
            }
        }

        long totalTime = (System.currentTimeMillis() - startTime) / 1000;

        Order fastestOrder = orders[0];

        for (Order order : orders) {
            if (order.getTotalTime() < fastestOrder.getTotalTime()) fastestOrder = order;
        }

        Kitchen.log("\nAll " + orderCount + " orders completed!");
        Kitchen.log("Total time spent: " + totalTime + " seconds");
        Kitchen.log("Fastest Order: #" + fastestOrder.orderNumber + " → " + fastestOrder.getTotalTime() + " seconds");
    }
}