package org.mapuna.ch4;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import org.mapuna.utils.Service;

import static org.mapuna.utils.Helpers.web;

public class Ch04Code05 {
    public static void main(String[] args) {
        Service.run();

        String name1 = "Yoda";
        String name2 = "Clement";

        web().get("/heroes").rxSend().map(HttpResponse::bodyAsJsonObject)
                .filter(json -> contains(name1, json))
                .subscribe(
                x -> System.out.println("Yes, " + name1 + " is a superhero."),
                Throwable::printStackTrace,
                () -> System.out.println("No, " + name1 + " is not a superhero.")
        );

        web().get("/heroes").rxSend().map(HttpResponse::bodyAsJsonObject)
                .filter(json -> contains(name2, json))
                .subscribe(
                        x -> System.out.println("Yes, " + name2 + " is a superhero."),
                        Throwable::printStackTrace,
                        () -> System.out.println("No, " + name2 + " is not a superhero.")
                );
    }

    private static boolean contains(String name, JsonObject json) {
        return json.stream().anyMatch(e -> e.getValue().toString().equalsIgnoreCase(name));
    }
}
