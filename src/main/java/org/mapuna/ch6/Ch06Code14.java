package org.mapuna.ch6;

import io.reactivex.Single;
import org.mapuna.utils.Supers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Sometimes it is easy to replace blocking code by non-blocking code. We can
 * either use another library that is non-blocking -- if the blocking code is
 * not ours, or we can find support in Rx, like in our example, because Rx has
 * support for delaying code.
 */
public class Ch06Code14 {
    private static Single<Supers> getBlockingVillain() {
        return Single.just(new Supers("Frog-Man",
                Arrays.asList("super-strength", "leaping", "super-agility", "french"),
                false))
                .delay(10000, TimeUnit.MILLISECONDS)
                .doAfterTerminate(() -> System.out.println("Operation done."));
    }

    /**
     * Problem here! We created a Single with a delay which is supposed to be emitted
     * later in the future, and we subscribed to it, but remember that all that is
     * non-blocking. So Rx planned for our Single to emit in the future, and returned
     * to the caller thread which called the main thread.
     *
     * And then we returned from the main thread, so the JVM shut down.
     *
     * Schedulers come to rescue here. {@see org.mapuna.Ch06Code15}.
     */
    public static void main(String[] args) {
        System.out.println("Before...");

        getBlockingVillain().subscribe(val -> System.out.println(val + "\nAfter!"));
    }
}
