package org.mapuna.ch7;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.vertx.core.json.JsonObject;
import org.mapuna.utils.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.mapuna.utils.Helpers.log;

public class Ch07Code07 {
    private static final int[] SUPER_HEROES_BY_ID = {641, 65, 37, 142};

    public static void main(String[] args) {

        Service.run(false);

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
                });

        log("---------------- Subscribing");
        observable
                .subscribeOn(Schedulers.io())
                .subscribe(
                        item -> {
                            log("Received " + item);
                        }, error -> {
                            log("Error");
                        }, () -> {
                            log("Complete");
                        });
        log("---------------- Subscribed");
    }
}
