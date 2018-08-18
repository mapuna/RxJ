package org.mapuna.ch3;

import io.reactivex.Observable;

public class Ch03Code05 {
    public static void main(String[] args) {
        Observable<String> stream = Observable.create(
                subscriber -> {
                    subscriber.onNext("Elektra");
                    subscriber.onNext("Black Widow");
                    subscriber.onNext("Wonder Woman");
                    subscriber.onError(new RuntimeException("Injected Error"));
                    subscriber.onNext("Black Canary");
                    subscriber.onComplete();
                }
        );

        stream.subscribe(
                s -> System.out.println(">> " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Completed")
        );
    }
}
