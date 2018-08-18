package org.mapuna.ch3;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Ch03Code01 {
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
                .doOnNext(s -> System.out.println("Next >> " + s))
                .doOnComplete(() -> System.out.println("Completed.")).subscribe();
    }
}
