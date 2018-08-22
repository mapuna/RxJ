package org.mapuna.ch7;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.mapuna.utils.Helpers;
import org.mapuna.utils.Supers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.mapuna.utils.Helpers.log;

/**
 * RxJava schedulers can change the behavior of the emitter and subscriber threads.
 * Typically, we can choose on which threads the emissions happen. A scheduler is
 * very similar to a Java Executor, it can actually be seen as a thread pool (most
 * of the time they are).
 *
 * RxJava makes concurrency and multi-threading much easier. It handles concurrency
 * using mainly two operators: subscribeOn() and observeOn().
 *
 * Some operators such as flatMap() can be combined with these two operators to
 * create concurrent data processing.
 *
 * WARNING!!! While RxJava can help we make safe and powerful concurrent applications,
 * we still need to be aware of the traps and pitfalls in multi-threading.
 *
 * 1. The subscribeOn operation can change the emitter thread. (This example)
 */
public class Ch07Code05 {
    private static List<String> SUPER_HEROES =
            Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");

    public static void main(String[] args) throws InterruptedException {
        Scheduler sched = Schedulers.from(newFixedThreadPool(10, Helpers.threadFactory));
        CountDownLatch latch = new CountDownLatch(1);

        // Synchronous emission on the main thread.
        Observable<Object> observable = Observable.create(
                emitter -> {
                    for (String sh: SUPER_HEROES) {
                        log("Emitting: " + sh);
                        emitter.onNext(sh);
                    }
                    log("Completing");
                    emitter.onComplete();
                }
        );

        log("--- Subscribing ---");
        observable.subscribeOn(sched)
                .subscribe(
                        item -> log("Received: " + item),
                        error -> log("Error"),
                        () -> {
                            log("Complete");
                            latch.countDown();
                        }
                );
        log("--- Subscribed ---");

        latch.await();
    }
}
