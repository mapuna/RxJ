package org.mapuna.ch3;

import io.reactivex.Observable;

import java.util.concurrent.atomic.AtomicInteger;

public class HotStream {

    static Observable<Integer> createStream() {
        AtomicInteger nSubscribers = new AtomicInteger();
        AtomicInteger counter = new AtomicInteger();

        return Observable.<Integer>create(
                subscriber -> {
                    new Thread(() -> {
                        while (nSubscribers.get() > 0) {
                            subscriber.onNext(counter.getAndIncrement());
                            takeANap();
                        }
                    }).start();
                }
        ).publish().autoConnect()
                .doOnSubscribe(s -> nSubscribers.getAndIncrement())
                .doOnDispose(() -> {
                    System.out.println("A subscriber has quit.");
                    nSubscribers.decrementAndGet();
                });
    }

    public static void takeANap() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    }

    public static void takeANap(int sec) {
        try {
            Thread.sleep(1000 * sec);
        } catch (InterruptedException e) {
            System.out.println("Interrupted.");
        }
    }
}
