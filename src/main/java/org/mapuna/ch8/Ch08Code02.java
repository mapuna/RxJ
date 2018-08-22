package org.mapuna.ch8;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

/**
 * Testing backpressure streams
 */
public class Ch08Code02 {
    public static void main(String[] args) {
        Flowable<Integer> flowable = Flowable.range(1, 10).filter(n -> n % 2 == 0);
        TestSubscriber<Integer> testSubscriber = flowable.test(0);

        testSubscriber
                .assertNever(n -> n % 2 == 1)
                .requestMore(2)
                .assertValues(2, 4)
                .assertNotComplete()
                .requestMore(3)
                .assertValues(2, 4, 6, 8, 10)
                .assertComplete();

        testSubscriber = flowable.test(0);

        testSubscriber
                .assertNotComplete()
                .requestMore(2)
                .assertValues(2, 4)
                .cancel();

        testSubscriber
                .assertNotComplete();
    }
}
