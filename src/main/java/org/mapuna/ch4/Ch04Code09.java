package org.mapuna.ch4;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.mapuna.ch3.HotStream;

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
public class Ch04Code09 {
    public static void main(String[] args) {
        Observable.range(1, 999_999_999).map(Item::new)
                // Emissions are made on the caller thread (main)
                // The next processing stages and the terminal subscriber
                // is now called on a separate thread (io thread).
                .observeOn(Schedulers.io())
                .subscribe(
                        item -> {
                            nap();
                            System.out.println("Received: " + item.i);
                        }
                );
        HotStream.takeANap(5);
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
