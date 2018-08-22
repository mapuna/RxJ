package org.mapuna.ch8;

import io.reactivex.subscribers.TestSubscriber;
import org.junit.jupiter.api.Test;
import org.mapuna.utils.Helpers;
import org.mapuna.utils.Supers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class GoodAsyncTest {
    @Test
    void theRightWayToTest() throws TimeoutException {
        TestSubscriber<Supers> testSubscriber = Helpers.heroes().test();

        if (!testSubscriber.awaitTerminalEvent(2, TimeUnit.SECONDS)) {
            throw new TimeoutException("It timed out!");
        }

        testSubscriber
                .assertComplete()
                .assertNoErrors()
                .assertValueCount(520);
    }
}
