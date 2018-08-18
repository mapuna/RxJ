package org.mapuna.ch3;

import io.reactivex.Observable;

import java.util.Scanner;

public class Ch03Code06 {
    public static void main(String[] args) {
        Observable<String> stream = Observable.create(
                subscriber -> {
                    boolean done = false;
                    Scanner scan = new Scanner(System.in);

                    while (!done) {
                        String input = scan.next();
                        if (input.contains("done")) {
                            done = true;
                            subscriber.onComplete();
                        } else if (input.contains("error")) {
                            subscriber.onError(new RuntimeException(input));
                        } else {
                            subscriber.onNext(input);
                        }
                    }
                }
        );

        stream.subscribe(
                s -> System.out.println(">> " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Completed")
        );
    }
}
