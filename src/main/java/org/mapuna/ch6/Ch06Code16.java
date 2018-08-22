package org.mapuna.ch6;

import io.reactivex.Single;
import org.mapuna.utils.Supers;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * In most cases, code will not run from the main thread and the JVM will
 * not terminate when a function is called, like in most back-end servers.
 *
 * So in order to emulate the JVM not terminating, there are several ways
 * to make it wait for the RX subscriber to be done, like sleeping longer.
 *
 * preferable option 1. for blocking the main thread, use a CountDownLatch.
 */
public class Ch06Code16 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Before...");

        CountDownLatch latch = new CountDownLatch(10);
        getBlockingVillain().subscribe(
                val -> {
                    System.out.println(val);
                    latch.countDown();
                });
        System.out.println("After!");
        latch.await();
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
