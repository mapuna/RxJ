package org.mapuna.ch3;

import io.reactivex.Observable;

/**
 * Hot streams
 *
 * Unlike cold streams, hot streams broadcast the same items to all listening
 * subscribers. However, if a subscriber arrives later, it won’t receive the
 * previous items. Logically, hot streams represent events or facts rather than
 * known finite data sets.
 *
 * For example, let’s imagine a counter. This counter is incremented every second,
 * and this value is emitted in a stream. When a subscriber starts listening to,
 * it only gets the data emitted after that.
 *
 * There are ways to transform cold stream into hot stream named ConnectableObservable.
 * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Connectable-Observable-Operators">Documentation</a>.
 */
public class Ch03Code08 {
    public static void main(String[] args) {
        Observable<Integer> stream = HotStream.createStream();

        stream.subscribe(
                s -> System.out.println("Stream A: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Stream A: Completed")
        );

        HotStream.takeANap(3);

        stream.subscribe(
                s -> System.out.println("Stream B: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Stream B: Completed")
        );
    }
}
