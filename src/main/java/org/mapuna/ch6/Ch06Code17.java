package org.mapuna.ch6;

import io.reactivex.Single;
import org.mapuna.utils.Supers;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Blocking main thread by transforming our Single into a JDK Future
 * and blocking on it (because get is blocking).
 */
public class Ch06Code17 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Before operation");
        Supers value = getBlockingSuperVillain().toFuture().get();
        System.out.println("Operation completed: " + value);
    }

    private static Single<Supers> getBlockingSuperVillain() {
        return Single.just(new Supers("Frog-Man",
                Arrays.asList("super strength", "leaping", "mega agility", "French"),
                false))
                .delay(1, TimeUnit.SECONDS)
                .doAfterTerminate(() -> System.out.println("Operation done"));
    }
}
