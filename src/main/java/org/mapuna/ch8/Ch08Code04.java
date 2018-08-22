package org.mapuna.ch8;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;

/**
 * Controlling the clock
 *
 * Some streams are time-sensitive, as they emit events with delays or an interval-based pace.
 * All of these streams need a scheduler to offload work at a later point in time.
 *
 * We could think of putting threads to sleep and wait for timing events to happen, but this
 * is error-prone (non-determinism) and it slows test execution dramatically.
 *
 * A better solution is to use a TestScheduler, a scheduler designed for testing. The good
 * thing with TestScheduler is that we are the master of time, and we advance it manually.
 * Here is an example where 2 Single are being zipped, producing a string value about 1
 * second from the subscription.
 */
public class Ch08Code04 {
    public static void main(String[] args) {
        TestScheduler scheduler = new TestScheduler();

        Single<Long> s1 = Single.timer(1, TimeUnit.SECONDS, scheduler);
        Single<String> s2 = Single.just("Hello");
        Single<String> r = Single.zip(s1, s2, (t, s) -> t + " -> " + s);

        TestObserver<String> testObserver = r.test();

        testObserver.assertNoValues();

        scheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS);
        testObserver.assertNoValues();

        scheduler.advanceTimeBy(600, TimeUnit.MILLISECONDS);
        testObserver
                .assertNoErrors()
                .assertValue("0 -> Hello");
    }
}
