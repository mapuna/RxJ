package org.mapuna.ch8;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import java.util.concurrent.TimeUnit;

/**
 * it is not always possible to control the clock with a TestScheduler.
 * A good example is an I/O operation: we can’t guarantee latency and we often use timeouts.
 * In such cases, we need to let the test run on clock time. The following example produces
 * string values every 500ms:
 */
public class Ch08Code05 {
    public static void main(String[] args) {
        Observable<String> strings = Observable.just("a", "b", "c", "d");
        Observable<Long> ticks = Observable.interval(500, TimeUnit.MILLISECONDS);
        Observable<String> stream = Observable.zip(ticks, strings, (t, s) -> s);

        TestObserver<String> testObserver = stream.test();
        if (testObserver.awaitTerminalEvent(3, TimeUnit.MILLISECONDS)) {
            testObserver
                    .assertNoErrors()
                    .assertComplete()
                    .assertValues("a", "b", "c", "d");
            System.out.println("Cool!");
        } else {
            System.err.println("It did not finish on time");
        }
    }
}
