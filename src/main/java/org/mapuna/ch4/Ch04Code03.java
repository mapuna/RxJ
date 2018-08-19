package org.mapuna.ch4;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import org.mapuna.utils.Helpers;
import org.mapuna.utils.Service;
import org.mapuna.utils.Supers;

/**
 * Singles are often used for asynchronous operations returning a single result,
 * such as an HTTP request. The following example uses the Vert.x Web Client to
 * retrieve a list of superheroes.
 */
public class Ch04Code03 {
    public static void main(String[] args) {
        Service.run();

        Helpers.web().get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject)
                .map(JsonObject::size)
                .subscribe(
                        length -> System.out.println("Number of heroes: " + length)
                );
    }
}
