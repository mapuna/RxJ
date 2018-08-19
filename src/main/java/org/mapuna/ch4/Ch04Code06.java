package org.mapuna.ch4;

import io.reactivex.Completable;

/**
 * Completable represents a stream not emitting a value but simply concerned with an
 * action being executed. As a consequence, it does not provide a doOnNext method as
 * there is no next.
 *
 * It indicates the successful completion of a (potentially asynchronous) process or
 * its failure.
 */
public class Ch04Code06 {
    public static void main(String[] args) {
        Completable.fromAction(
                () -> System.out.println("Hello!")
        ).subscribe(
                () -> System.out.println("OK"),
                err -> System.err.println("KO")
        );
    }
}
