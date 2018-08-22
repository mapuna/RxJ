package org.mapuna.ch8;

import org.junit.jupiter.api.Test;
import org.mapuna.utils.Helpers;
import org.mapuna.utils.Supers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

/**
 * We are going to test an HTTP request to a third-party service that returns 520 superheroes.
 * A naive way to test is to assume traditional synchronous semantics and just observe what
 * the stream emits:
 */
class BadAsyncTest {
    private static class TBox {
        private Throwable t;

        void set(Throwable t) {
            this.t = t;
        }
    }

    @Test
    void theWrongWayToTest() throws Throwable {
        TBox box = new TBox();
        ArrayList<Supers> stuffs = new ArrayList<>();

        Helpers.heroes()
                .subscribe(stuffs::add, box::set, () -> System.out.println("[ done ]"));

        Thread.sleep(5000);

        if (box.t != null) {
            throw box.t;
        }

        assertEquals(stuffs.size(), 520);
    }
}
