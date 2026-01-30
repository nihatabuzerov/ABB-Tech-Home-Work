package org.example;

public class Kitchen {

    public synchronized static void log(String message) {
        System.out.println(message);
    }
}