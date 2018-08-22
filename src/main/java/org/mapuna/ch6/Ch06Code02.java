package org.mapuna.ch6;

import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.file.FileSystem;

/**
 * Rx API on top of Rx API
 * Reading a file with an Rx implementation (Vert.x)
 */
public class Ch06Code02 {

    private static Single<JsonArray> getFile() {
        Vertx vertx = Vertx.vertx(); // Reactive core of Vert.x
        FileSystem fs = vertx.fileSystem();
        // Read a file and return the read buffer as a Single<String>
        return fs.rxReadFile("src/main/resources/characters.json")
                .map(Buffer::toString)
                .map(JsonArray::new);
    }

    public static void main(String[] args) {
        getFile()
                .map(JsonArray::encodePrettily)
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace
                );
    }
}
