package org.mapuna.ch5;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import org.mapuna.utils.Service;

import static org.mapuna.utils.Helpers.web;

/**
 * can also use flatMap on a Single to execute a second request once the Single has completed.
 */
public class Ch05Code11 {
    public static void main(String[] args) {
        Service.run();
        Single<JsonObject> request1 = web()
                .get("/heroes")
                .rxSend()
                .map(HttpResponse::bodyAsJsonObject);

        request1
                .flatMapObservable(j -> Observable.fromIterable(j.fieldNames()))
                // Take the first one, as a Single
                .firstOrError()
                // Second request
                .flatMap(Ch05Code11::getHero)
                .subscribe(json -> System.out.println(json.encodePrettily()));
    }

    private static Single<JsonObject> getHero(String s) {
        return web().get("/heroes/" + s).rxSend().map(HttpResponse::bodyAsJsonObject);
    }
}
