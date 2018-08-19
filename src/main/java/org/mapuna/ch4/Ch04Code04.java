package org.mapuna.ch4;

import io.reactivex.Maybe;

/**
 * Maybe is a stream that can emit 0 or 1 item. It is useful because Single can’t emit null
 * (null is an illegal value). Maybe observers are notified:
 * - when a value is emitted using the onSuccess method,
 * - when the stream complete, without a value using the onComplete method,
 * - when an error is thrown using the onError method
 * Notice the subtlety about onSuccess and onComplete.
 * The first one is called when there is a value. The second one is called when there is not.
 */
public class Ch04Code04 {
    public static void main(String[] args) {
        Maybe.just("Supergirl").subscribe(
                name -> System.out.println("Stream A " + name),
                Throwable::printStackTrace,
                () -> System.out.println("Stream A completed.")
        );

        Maybe.empty().subscribe(
                name -> System.out.println("Stream B " + name + " (not called)."),
                Throwable::printStackTrace,
                () -> System.out.println("Stream B completed.")
        );
    }
}
