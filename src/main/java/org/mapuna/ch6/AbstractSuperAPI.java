package org.mapuna.ch6;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import io.vertx.reactivex.core.file.FileSystem;
import org.mapuna.utils.Supers;

public class AbstractSuperAPI implements SuperAPI {

    Flowable<Supers> load() {
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

    @Override
    public Single<Supers> hero() {
        return Single.error(new UnsupportedOperationException());
    }

    @Override
    public Single<Supers> villain() {
        return Single.error(new UnsupportedOperationException());
    }

    @Override
    public Flowable<Supers> heroes() {
        return Flowable.empty();
    }

    @Override
    public Flowable<Supers> villains() {
        return Flowable.empty();
    }

    @Override
    public Maybe<Supers> findByName(String name) {
        return Maybe.empty();
    }

    @Override
    public Single<Supers> findByNameOrError(String name) {
        return Single.error(new RuntimeException("Not Found!"));
    }
}
