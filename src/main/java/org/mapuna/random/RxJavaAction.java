package org.mapuna.random;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJavaAction {
    public static void main(String[] args) {
        Observable.range(1, 5).subscribe(
                System.out::println,
                error -> System.out.println(error.getMessage()),
                () -> System.out.println("Completed!")
        );

        hello("Anupam", "Priya", "Lavanya");

        Maybe.just(1)
                //.map(v -> v + 1)
                .filter(v -> v == 1)
                .defaultIfEmpty(2)
                .test()
                .assertResult(1);

        Observable.just("Ambujam", "Anupam", "Priya", "Goofy", "Lavanya")
                .observeOn(Schedulers.io())
                .subscribe(
                        new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                if (!d.isDisposed())
                                    System.out.println("Subscribed");
                            }

                            @Override
                            public void onNext(String s) {
                                System.out.println(s);
                            }

                            @Override
                            public void onError(Throwable e) {
                                System.err.println(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                System.out.println("Completed");
                            }
                        }
                );
    }

    private static void hello(String... names) {
        Observable.fromArray(names).subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                System.out.println("Hello " + s + "!");
            }
        });
    }
}
