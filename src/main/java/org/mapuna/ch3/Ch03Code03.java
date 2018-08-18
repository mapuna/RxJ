package org.mapuna.ch3;

import io.reactivex.Observable;

public class Ch03Code03 {

    public static void main(String[] args) {
        Observable
                .just("Superman", "Batman", "Aquaman", "Asterix", "Ironman")
                .subscribe(
                        s -> System.out.println(">> " + s),
                        Throwable::printStackTrace,
                        () -> System.out.println("Completed")
                );
    }
}
