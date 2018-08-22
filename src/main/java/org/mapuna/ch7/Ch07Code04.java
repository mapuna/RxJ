package org.mapuna.ch7;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

import static org.mapuna.utils.Helpers.log;
import static org.mapuna.utils.Helpers.sleep;

/**
 * Asynchronous emissions
 *
 * When we write long pipelines, with multiple stages, if one of the stage blocks,
 * we block the next emission. While sometimes it’s what we want to do, but we habe
 * to keep in mind that if we are observing a hot stream, the emissions will be
 * buffered somewhere (if we don’t or can’t use back pressure).
 */
public class Ch07Code04 {
    private static List<String> SUPER_HEROES =
            Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");

    public static void main(String[] args) {
        Observable<Object> observable = Observable.create(
                // Emitter emits on a new thread -- Async
                emitter -> new Thread(
                        () -> {
                            for (String sh: SUPER_HEROES) {
                                // Add blocking
                                // sleep(300);
                                log("Emitting: " + sh);
                                emitter.onNext(sh);
                            }
                            log("Completing");
                            emitter.onComplete();
                        }).start()
        );

        log("--- Subscribing ---");
        // observer/subscriber is on the main thread.
        observable
                // Adding delay here
                .doOnNext(x -> sleep(300))
                .subscribe(
                        item -> log("Received: " + item),
                        error -> log("Error"),
                        () -> log("Complete")
                );
        log("--- Subscribed ---");
    }
}
