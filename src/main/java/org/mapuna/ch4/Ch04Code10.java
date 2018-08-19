package org.mapuna.ch4;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.mapuna.ch3.HotStream;

/**
 * In {@see Ch04Code09}, the emissions of the numbers are too fast for the consumer,
 * and because the emissions are being pushed into an unbounded buffer by observeOn,
 * this can be the source of many problems such as... running out of memory.
 *
 * To mitigate this issue, RX Java 2 provides a stream named Flowable. Flowable is
 * like Observable (it may contain multiple items) but implements a back-pressure
 * protocol. This protocol tells the source stream to emit items at a pace specified
 * by the consumer. Flowable uses a protocol named Reactive Streams. This
 * specification has been introduced in Java 9 under the name java.util.concurrent.Flow.
 *
 * RX Java 2 provides some other back pressure strategies such as using buffers, or
 * dropping data.
 */
public class Ch04Code10 {
    public static void main(String[] args) {
        Flowable.range(1, 1000000)
                .map(Item::new)
                .observeOn(Schedulers.io())
                .subscribe(
                        item -> {
                            // nap();
                            System.err.println("Received: " + item.i);
                        },
                        Throwable::printStackTrace,
                        System.out::println
                );
        HotStream.takeANap(5);
    }

    private static class Item {
        private final int i;

        Item(int number) {
            System.out.println("Constructing item using " + number);
            this.i = number;
        }
    }

    private static void nap() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
