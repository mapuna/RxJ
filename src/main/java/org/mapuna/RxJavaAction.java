package org.mapuna;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class RxJavaAction {
    public static void main(String[] args) {
        Observable.range(1, 5).subscribe(
                System.out::println,
                error -> System.out.println(error.getMessage()),
                () -> System.out.println("Completed!")
        );

        hello("Anupam", "Priya", "Lavanya");
    }

    public static void hello(String... names) {
        Observable.fromArray(names).subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                System.out.println("Hello " + s + "!");
            }
        });
    }
}
