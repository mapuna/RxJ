package org.mapuna.ch7;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

import static org.mapuna.utils.Helpers.log;
import static org.mapuna.utils.Helpers.sleep;

/**
 * Asynchronous emissions
 *
 * Asynchronous emission happens when an Emitter is invoked asynchronously by a different
 * thread than the subscription thread. With this example:
 *      - 2 threads are involved, main and Thread-0 created using new Thread(…​).start()
 *      - the main thread is the application thread
 *      - the Thread-# thread is used for emitting the items
 *      - each emission is followed exactly by its corresponding reception (like before)
 *      because it reuses the emitter thread
 *      - the subscription happens and returns immediately before the actual observable
 *      completion happens
 * In this situation the application’s main thread will not clearly be impacted by the
 * API pauses, however, some parts of the application will now be executed on Thread-#
 * thread. If the code chooses to block (for example using a blocking method), then the
 * emitter thread will be impacted by the blocking. This means that it won’t be able to
 * emit another item until the thread is unblocked.
 */
public class Ch07Code03 {
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
        observable.subscribe(
                item -> log("Received: " + item),
                error -> log("Error"),
                () -> log("Complete")
        );
        log("--- Subscribed ---");
    }
}
