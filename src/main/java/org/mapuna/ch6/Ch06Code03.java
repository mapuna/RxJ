package org.mapuna.ch6;

import io.reactivex.Flowable;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.file.FileSystem;
import org.mapuna.utils.Supers;

/**
 * Rx API on top of Rx API
 * Reading a file with an Rx implementation (Vert.x)
 */
public class Ch06Code03 {

    private static Flowable<Supers> load() {
        Vertx vertx = Vertx.vertx(); // Reactive core of Vert.x
        FileSystem fs = vertx.fileSystem();
        // Read a file and return the read buffer as a Single<String>
        return fs.rxReadFile("src/main/resources/characters.json")
                .map(Buffer::toString)
                .map(JsonArray::new)
                .flatMapPublisher(Flowable::fromIterable)
                .cast(JsonObject.class)
                .map(json -> json.mapTo(Supers.class));
    }

    public static void main(String[] args) {
        load()
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace
                );
    }
}
