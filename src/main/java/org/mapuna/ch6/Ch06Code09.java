package org.mapuna.ch6;

import io.reactivex.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Exercise: Following code needs cleanup code!
 *
 * Some objects require a manual clean-up after they’re not used anymore. Closing sockets,
 * freeing buffers, returning pooled objects, there are many examples of resources that
 * need to be cleaned up.
 *
 * We use the doFinally() method after an Observable is finished. This method is called in
 * any completion case (success or exception).
 */
public class Ch06Code09 {
    private static final Path DIRECTORY = new File("src/main/resources/super/heroes").toPath();

    public static void main(String[] args) {
        getHeroesNames().subscribe(System.out::println, Throwable::printStackTrace);
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
