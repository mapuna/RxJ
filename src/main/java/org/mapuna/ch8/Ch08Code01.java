package org.mapuna.ch8;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

/**
 * RX Java pipelines provide a special operator called test() that returns instances
 * of io.reactivex.observers.TestObserver. As the name suggests, a TestObserver provides
 * support for checking what an observable does.
 */
public class Ch08Code01 {
    public static void main(String[] args) {
        TestObserver<Integer> testObserver = Observable.range(1, 11)
                .filter(n -> n % 2 == 0)
                .test();

        testObserver
                .assertSubscribed()
                .assertNever(n -> n % 2 == 1)
                .assertComplete()
                .assertValueCount(5)
                .assertValues(2, 4, 6, 8, 10);
    }
}
