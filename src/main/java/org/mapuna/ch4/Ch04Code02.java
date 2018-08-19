package org.mapuna.ch4;

import io.reactivex.Single;

/**
 * A `Single' is a specialized stream that only emits one item. It works like the
 * `Observable' streams we have seen previously but is limited to operators that
 * make sense for a single emission.
 *
 * Typically, doOnNext and doOnComplete are replaced by doOnSuccess that accept
 * the produced item.
 */
public class Ch04Code02 {
    public static void main(String[] args) {
        Single.just("Thanos")
                .doOnSuccess(s -> System.out.println("Hello, " + s + "!"))
                .subscribe();
    }
}
