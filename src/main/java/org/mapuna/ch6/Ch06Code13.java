package org.mapuna.ch6;

import org.mapuna.utils.Supers;

import java.util.Arrays;

/**
 * Often, the hard part will be to simply avoid blocking, so let’s see a simple strategy for that.
 */
public class Ch06Code13 {
    private static Supers getBlockingVillain() {
        System.out.println("Starting...");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("Operation done.");
        return new Supers("Frog-Man",
                Arrays.asList("super-strength", "leaping", "super-agility", "french"),
                false);
    }

    public static void main(String[] args) {
        System.out.println("Before...");
        Supers hero = getBlockingVillain();
        System.out.println("After!");
    }
}
