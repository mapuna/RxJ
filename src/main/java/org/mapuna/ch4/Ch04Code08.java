package org.mapuna.ch4;

import io.reactivex.Observable;

import static org.mapuna.ch3.HotStream.takeANap;

/**
 * So far, we have seen examples of streams that were pushing items to subscribers.
 * However, there is an issue with this model. If your consumer cannot keep up with
 * the pace, something bad is going to happen. Putting a buffer in between will
 * only handle small bumps.
 *
 * This is where back-pressure comes into the picture. But first, let’s illustrate
 * the example.
 *
 * We create a stream of integers and display them in the subscribe method after
 * a small nap.
 */
public class Ch04Code08 {
    public static void main(String[] args) {
        Observable.range(1, 10000).map(Item::new)
                .subscribe(
                        item -> {
                            nap();
                            System.out.println("Received: " + item.i);
                        }
                );
    }

    private static void nap() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static class Item {
        private final int i;

        Item(int number) {
            System.out.println("Constructing item using: " + number);
            this.i = number;
        }
    }
}
