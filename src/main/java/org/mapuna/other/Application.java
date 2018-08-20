package org.mapuna.other;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        System.out.println("Enter text: ");
        EventSource eventSource = new EventSource();

        eventSource.addObserver((observable, o) -> System.out.println("Received response: " + o));

        new Thread(eventSource).start();
    }
}
