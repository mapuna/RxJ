package org.mapuna.ch6;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * In most cases we want to return an RX type that will execute its operation
 * for every subscriber. In some cases, though, we have to write an API that
 * forces us to allocate resources that have to be consumed (to avoid leaks)
 * before you can return an RX type.
 *
 * Or sometimes we want to create an Observable that can only be subscribed once.
 *
 * In cases like these, if we return the Rx type, the user can choose to never
 * subscribe, or subscribe more than once.
 *
 * In order to force the user to consume the resource, we can turn things around
 * and require the user to provide with an Observer instead.
 */
public class Ch06Code12 {
    public static final Path DIRECTORY =
            new File("src/main/resources/super/heroes").toPath();

    public static void getNamesWithForcedConsumption(Observer<String> subscriber) {
        Observable.<Path>create(emitter -> {
            DirectoryStream<Path> stream = null;
            try {
                stream = Files.newDirectoryStream(DIRECTORY);
            } catch (IOException e) {
                emitter.onError(e);
            }
            for (Path path: Objects.requireNonNull(stream))
                emitter.onNext(path);
            stream.close();
            emitter.onComplete();
        }).map(path -> path.toFile().getName())
        .subscribe(subscriber);
    }

    public static void main(String[] args) {
        getNamesWithForcedConsumption(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(String s) {
                System.out.println("File: " + s);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {}
        });
    }
}
