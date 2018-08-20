package org.mapuna.ch5;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import org.mapuna.utils.Service;

import static org.mapuna.utils.Helpers.web;

/**
 * FlatMap is also used as a composing operator to express a sequential composition.
 * For example, chaining two HTTP requests in this example.
 */
public class Ch05Code10 {
    public static void main(String[] args) {
        Service.run();

        Single<JsonObject> request1 =
                web().get("/heroes").rxSend().map(HttpResponse::bodyAsJsonObject);
        request1.
                // Transform the response to retrieve a stream of ids
                flatMapObservable(j -> Observable.fromIterable(j.fieldNames()))
                // Take the first one -- This gives an observable of 1 element
                .take(1)
                // Another request
                .flatMapSingle(Ch05Code10::getHero)
                .subscribe(json -> System.out.println(json.encodePrettily()));
    }

    private static Single<JsonObject> getHero(String s) {
        return web().get("/heroes/" + s).rxSend().map(HttpResponse::bodyAsJsonObject);
    }
}
