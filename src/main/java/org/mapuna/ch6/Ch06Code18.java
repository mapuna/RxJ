package org.mapuna.ch6;

import io.reactivex.Single;
import org.mapuna.utils.Supers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Block simply by calling the Single.blockingGet method
 */
public class Ch06Code18 {
    public static void main(String[] args) {
        System.out.println("Before operation");
        Supers value = getBlockingSuperVillain().blockingGet();
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
