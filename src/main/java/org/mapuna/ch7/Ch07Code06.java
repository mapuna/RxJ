package org.mapuna.ch7;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.mapuna.utils.Helpers;

import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.mapuna.utils.Helpers.log;

/**
 * 2. The observeOn operations can change the subscriber thread.
 */
public class Ch07Code06 {
    private static List<String> SUPER_HEROES =
            Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");

    public static void main(String[] args) {
        Scheduler sched = Schedulers.from(newFixedThreadPool(10, Helpers.threadFactory));

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

        log("---------------- Subscribing");
        observable
                .observeOn(sched)
                .subscribe(
                        item -> log("Received " + item),
                        error -> log("Error"),
                        () -> log("Complete"));
        log("---------------- Subscribed");
    }
}
