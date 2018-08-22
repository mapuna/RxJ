package org.mapuna.ch7;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

import static org.mapuna.utils.Helpers.log;

/**
 * Synchronous emissions
 *
 * Concurrency in RxJava is simple to execute, but somewhat difficult to understand.
 * By default, streams execute work on the caller thread, which is the thread that
 * subscribed it. n many of our earlier examples, this was the main thread that kicked
 * off our main method.
 *
 * This synchronous emission happens when an Emitter (as the ones used in ch6 code) is
 * invoked during the subscription. This invocation is made by the thread that is
 * executing the subscription (by calling .subscribe()). This behavior is described
 * *again* here: Emitter will emit on the calling (main) thread here.
 *
 * When running this example, you can see a few important points:
 *      - the main thread is the only one involved and all operations are executed on this thread
 *      - each emission is followed exactly by its corresponding reception
 *      - the next statement after the subscription operation happens after all the emissions,
 *      meaning that the stream has been completely consumed before.
 */
public class Ch07Code01 {
    private static List<String> SUPER_HEROES =
            Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");

    public static void main(String[] args) {
        Observable<Object> observable = Observable.create(
                emitter -> {
                    for (String sh: SUPER_HEROES) {
                        log("Emitting: " + sh);
                        emitter.onNext(sh);
                    }
                    emitter.onComplete();
                }
        );

        log("--- Subscribing ---");
        observable.subscribe(
                item -> log("Received: " + item),
                error -> log("Error"),
                () -> log("Complete")
        );
        log("--- Subscribed ---");
    }
}
