package org.mapuna.ch2;

import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class Ch02Code02 {
    private static List<String> SUPER_HEROS = Arrays.asList(
            "Captain Americs",
            "Spiderman",
            "Hulk",
            "Supergirl",
            "Wonder Woman",
            "Rocket",
            "Doctor Strange"
    );

    public static void main(String[] args) {
        Observable
                .fromIterable(SUPER_HEROS)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("S"))
                .subscribe(System.out::println);
    }
}
