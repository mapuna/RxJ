package org.mapuna.ch5;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * FlatMap takes each item emitted by the observable stream and maps it to another stream.
 * Then, it merges the emissions from the returned streams into a single stream.
 *
 * flatMap returns a stream of the same type. So Single.flatMap returns a Single.
 * The methods flatMapCompletable, flatMapMaybe and flatMapPublisher (for Flowable) let
 * us transform the type of streams.
 */
public class Ch05Code09 {
    public static void main(String[] args) {
        String text = "Super heroes are super villains!";
        Single.just(text)
                .flatMapPublisher(s -> Flowable.fromArray(s.split(" ")))
                .subscribe(System.out::println);
    }
}
