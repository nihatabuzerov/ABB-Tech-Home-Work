package org.example;


public class Order extends Thread implements Runnable {

    int orderNumber;
    long totalTime, startTime;

    public void setOrderNumber (int orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public void run () {

        startTime = System.currentTimeMillis();
        Kitchen.log("*** Order #" + orderNumber + " *** Started.");

        for (Stages stage : Stages.values()) {
            executeStage(stage);
        }

        totalTime = (System.currentTimeMillis() - startTime) / 1000;

        Kitchen.log("*** Order #" + orderNumber + " *** Pizza delivered!");
        Kitchen.log("*** Order #" + orderNumber + " *** Total time: " + totalTime + " seconds");
    }

    private void executeStage (Stages stage) {

        try {
            String startMessage;
            String endMessage;

            startMessage = switch (stage) {
                case PREPARE -> "Preparing pizza...";
                case BAKE -> "Baking pizza...";
                case DELIVER -> "Delivering pizza to customer...";
            };

            endMessage = switch (stage) {
                case PREPARE -> "Preparation complete.";
                case BAKE -> "Baking complete.";
                case DELIVER -> null;
            };

            Kitchen.log("*** Order #" + orderNumber + " *** " + startMessage);

            int duration = stage.getRandomDuration();
            Thread.sleep(duration * (long) 1000);

            if (endMessage != null) {
                Kitchen.log("*** Order #" + orderNumber + " *** " + endMessage);
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public long getTotalTime () {
        return totalTime;
    }
}
