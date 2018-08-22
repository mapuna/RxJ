package org.mapuna.ch6;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.mapuna.utils.Supers;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Sometimes we just have to use blocking APIs, even though we want to expose
 * an RX API which can’t block the current thread.
 *
 * In this case, we can offload our blocking calls to a new thread, or a
 * thread-pool, or a special scheduler, whatever we want.
 */
public class Ch06Code19 {
    public static void main(String[] args) {
        System.out.println("Before operation");
        Supers result = getBlockingSuperVillain().blockingGet();
        System.out.println("After operation: " + result);
    }

    private static Single<Supers> getBlockingSuperVillain() {
        return Single.create(emitter ->
                /*
                Emitter emits on a new blocking thread.
                 */
                new Thread(() -> {
                    System.out.println("Operation starting");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        emitter.onError(e);
                        return;
                    }
                    emitter.onSuccess(new Supers("Frog-Man",
                            Arrays.asList("super strength", "leaping", "mega agility", "French"),
                            false));
                }).start()
        );
    }
}
