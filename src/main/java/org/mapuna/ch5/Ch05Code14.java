package org.mapuna.ch5;

import io.vertx.reactivex.ext.web.client.HttpResponse;
import io.vertx.reactivex.ext.web.codec.BodyCodec;
import org.mapuna.utils.Supers;

import static org.mapuna.utils.Helpers.web;

/**
 * Error Recovery
 */
public class Ch05Code14 {
    public static void main(String[] args) {
        web()
                .get("/heroes/random")
                .as(BodyCodec.json(Supers.class))
                .rxSend()
                .map(HttpResponse::body)
                .map(Supers::getName)
                .subscribe(
                        name -> System.out.println("Retrieved " + name),
                        err -> System.err.println("Something bad happened. " + err)
                );
    }
}
