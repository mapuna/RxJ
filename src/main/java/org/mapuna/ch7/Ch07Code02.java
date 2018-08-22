package org.mapuna.ch7;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

import static org.mapuna.utils.Helpers.log;
import static org.mapuna.utils.Helpers.sleep;

/**
 * Synchronous emission is not much of a problem when no latency is involved, i.e when the operations
 * only involve pure computation and don’t involve operations that will pause the thread of execution.
 *
 * However, when an operation has latency, the synchronous emission will impose this latency to the
 * subscribing thread like we can see in this example. We can see that the artificial delay in the
 * emission impacts the application code that receives the items.
 */
public class Ch07Code02 {
    private static List<String> SUPER_HEROES =
            Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");

    public static void main(String[] args) {
        Observable<Object> observable = Observable.create(
                emitter -> {
                    for (String sh: SUPER_HEROES) {
                        sleep(1000);
                        log("Emitting: " + sh);
                        emitter.onNext(sh);
                    }
                    log("...Completing.");
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
