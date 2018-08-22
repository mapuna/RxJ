package org.mapuna.ch6;

import io.reactivex.Observable;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * In cases where we *cannot* use either a base Rx-API or an asynchronous API.
 * It is possible to create Rx-APIs in such cases (Single, Flowable, Observable,
 * Maybe, Completable) from scratch with an Emitter.
 *
 * In this example, we are building a *synchronous* API, so it may not be of much use.
 */
public class Ch06Code08 {
    private static final Path directory
            = new File("/data/anupam/src/jsrc/RxJ").toPath();

    private static Observable<String> getFileNames() {
        return Observable.create(
                emitter -> {
                    Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
                            // Emit something
                            emitter.onNext(path.toFile().getPath());
                            return FileVisitResult.CONTINUE;
                        }
                    });
                    // Complete emission
                    emitter.onComplete();
                }
        );
    }

    public static void main(String[] args) {
        getFileNames()
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace
                );
    }
}
