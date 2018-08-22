package org.mapuna.ch6;

import io.reactivex.Single;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.file.FileSystem;

/**
 * Rx API on top of Rx API
 * Reading a file with an Rx implementation (Vert.x)
 */
public class Ch06Code01 {

    private static Single<String> getFile() {
        Vertx vertx = Vertx.vertx(); // Reactive core of Vert.x
        FileSystem fs = vertx.fileSystem();
        // Read a file and return the read buffer as a Single<String>
        return fs.rxReadFile("src/main/resources/characters.json")
                .map(Buffer::toString);
    }

    public static void main(String[] args) {
        getFile()
            .subscribe(
                    System.out::println,
                    Throwable::printStackTrace
            );
    }
}
