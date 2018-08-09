package org.mapuna;

import java.util.Observable;
import java.util.Scanner;

/**
 * takes keyboard input and treats each input line as an event.
 */
public class EventSource extends Observable implements Runnable {
    @Override
    public void run() {
        while (true) {
            String response = new Scanner(System.in).nextLine();
            setChanged();
            notifyObservers(response);
        }
    }
}
