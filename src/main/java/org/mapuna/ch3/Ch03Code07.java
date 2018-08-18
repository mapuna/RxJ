package org.mapuna.ch3;

import io.reactivex.Observable;

/**
 * Cold streams
 *
 * A cold stream restarts from the beginning for each subscriber,
 * and every subscriber gets the full set of items
 */
public class Ch03Code07 {
    public static void main(String[] args) {
        Observable<String> stream =
                Observable.just("Ambujam", "Anupam", "Priya", "Goofy", "Lavanya");

        stream.subscribe(
                s -> System.out.println("Stream A: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Stream A: Completed")
        );

        System.out.println();

        stream.subscribe(
                s -> System.out.println("Stream B: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Stream B: Completed")
        );
    }
}
