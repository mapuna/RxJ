package org.mapuna.ch5;

import io.reactivex.Single;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.codec.BodyCodec;
import org.mapuna.utils.Service;
import org.mapuna.utils.Supers;

import static org.mapuna.utils.Helpers.web;

public class Ch05Code13 {
    public static void main(String[] args) {
        Service.run();

        Single<Supers> random_heroes = web()
                .get("/heroes/random")
                .as(BodyCodec.json(Supers.class))
                .rxSend()
                .map(HttpResponse::body);

        Single<Supers> random_villains = web()
                .get("/villains/random")
                .as(BodyCodec.json(Supers.class))
                .rxSend()
                .map(HttpResponse::body);

        // Associate the items emitted by both single and call the fight method.
        // In the subscribe print the returned json object (using encodePrettily).
        random_heroes.
                zipWith(random_villains, Ch05Code13::fight)
                .subscribe(json -> System.out.println(json.encodePrettily()));
    }

    private static JsonObject fight(Supers h, Supers v) {
        String winner = h.getName();

        if (v.getSuperpowers().size() > h.getSuperpowers().size()) {
            winner = v.getName();
        } else if (v.getSuperpowers().size() == h.getSuperpowers().size()) {
            winner = "none";
        }

        return new JsonObject()
                .put("hero", h.getName())
                .put("villain", v.getName())
                .put("winner", winner);
    }
}
