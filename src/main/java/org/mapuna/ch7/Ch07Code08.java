package org.mapuna.ch7;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Context;
import io.vertx.reactivex.core.RxHelper;
import io.vertx.reactivex.core.Vertx;
import org.mapuna.utils.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.mapuna.utils.Helpers.log;

public class Ch07Code08 {
    private static final int[] SUPER_HEROES_BY_ID = {641, 65, 37, 142};

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        Context context = vertx.getOrCreateContext();
        Service.run(false);

        Scheduler contextScheduler = RxHelper.scheduler(context);

        Observable<String> observable = Observable.create(
                emitter -> {
                    for (int superHeroId : SUPER_HEROES_BY_ID) {
                        // Load a super hero using the blocking URL connection
                        URL url = new URL("http://localhost:8080/heroes/" + superHeroId);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        rd.close();
                        String superHero = new JsonObject(result.toString()).getString("name");
                        log("Emitting: " + superHero);
                        emitter.onNext(superHero);
                    }
                    log("Completing");
                    emitter.onComplete();
        })
                // execute the emitter on the io scheduler
                .observeOn(Schedulers.io()).cast(String.class);
                // execute the subscriber on the context thread

        // Execute a Vert.x event loop task
        context.runOnContext(v -> {
            log("---------------- Subscribing");
            observable
                    .subscribeOn(contextScheduler)
                    .subscribe(
                            item -> log("Received " + item),
                            error -> log("Error"),
                            () -> log("Complete"));
            log("---------------- Subscribed");
        });
    }
}
