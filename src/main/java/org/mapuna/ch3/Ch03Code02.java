package org.mapuna.ch3;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Ch03Code02 {
    private static List<String> SUPER_HEROS = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Ironman"
    );

    public static void main(String[] args) {
        Observable
                .fromIterable(SUPER_HEROS)
                .map(s -> {
                    if (s.endsWith("x"))
                        throw new RuntimeException("Something wrong!");
                    return s.toUpperCase();
                })
                .doOnNext(s -> System.out.println(" >> " + s))
                .doOnComplete(() -> System.out.println("Completed."))
                .doOnError(e -> System.out.println(e.getMessage()))
                .subscribe();
    }
}
