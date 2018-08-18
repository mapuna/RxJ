package org.mapuna.ch3;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Cleanup is always a good thing. In {@see org.mapuna.ch3.Ch03Code09},
 * we had 2 subscribers.
 * This time, we cancel the subscription after some time.
 */
public class Ch03Code09 {
    public static void main(String[] args) {
        Observable<Integer> stream = HotStream.createStream();

        Disposable s1 = stream.subscribe(
                s -> System.out.println("Stream A: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Stream A: Completed")
        );

        HotStream.takeANap(3);

        Disposable s2 = stream.subscribe(
                s -> System.out.println("Stream B: " + s),
                Throwable::printStackTrace,
                () -> System.out.println("Stream B: Completed")
        );

        HotStream.takeANap(5);

        s1.dispose();

        HotStream.takeANap(5);

        s2.dispose();
    }
}
