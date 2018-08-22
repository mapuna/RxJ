package org.mapuna.ch6;

import io.reactivex.Single;
import org.mapuna.utils.Supers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * In most cases, code will not run from the main thread and the JVM will
 * not terminate when a function is called, like in most back-end servers.
 *
 * So in order to emulate the JVM not terminating, there are several ways
 * to make it wait for the RX subscriber to be done, like sleeping longer.
 *
 * Bad Idea this:
 * Here thanks to a malicious Thread.sleep we can ask the main thread to
 * wait a little bit longer -- that is block the thread.
 */
public class Ch06Code15 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Before...");
        getBlockingVillain().subscribe(val -> System.out.println(val + "\nAfter!"));

        Thread.sleep(11000);
    }

    /**
     * This API method is non blocking!
     * @return Single<T>.
     */
    private static Single<Supers> getBlockingVillain() {
        return Single.just(
                new Supers("Frog-Man",
                        Arrays.asList("super-strength", "leaping", "super-agility", "french"),
                        false)
        ).delay(10000, TimeUnit.MILLISECONDS).doAfterTerminate(
                () -> System.out.println("Operation done.")
        );
    }
}
