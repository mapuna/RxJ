package org.mapuna.ch6;

import io.reactivex.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * In {@see org.mapuna.ch6.Ch06Code09}, we inadvertently tripped on a common Rx user
 * mistake of doing the heavy work before returning the Observable: This means that
 * the caller of getHeroesNames will pay the cost even if nobody ever subscribes to
 * the returned Observable. This can be acceptable in some cases, but in most cases,
 * we want the operations to only be done when someone subscribes to the Observable.
 *
 * There is another reason to want to do operations for a subscriber: it makes the
 * Observable re-usable for more than one subscriber. This example demonstrates the problem
 * if you subscribe more than once.
 *
 * For solution, {@see org.mapuna.ch6.Ch06Code11}.
 */
public class Ch06Code10 {
    private static final Path DIRECTORY = new File("src/main/resources/super/heroes").toPath();

    public static void main(String[] args) {
        Observable<String> files = getHeroesNames();
        files.subscribe(value -> System.out.println("Subscriber 1: " + value),
                Throwable::printStackTrace);
        files.subscribe(value -> System.out.println("Subscriber 2: " + value),
                Throwable::printStackTrace);
    }

    private static Observable<String> getHeroesNames() {
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(DIRECTORY);
        } catch (IOException e) {
            return Observable.error(e);
        }
        return Observable.fromIterable(stream)
                .map(path -> path.toFile().getName())
                .doFinally(stream::close);
    }
}
