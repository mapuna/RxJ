package org.mapuna.utils;

import io.reactivex.Completable;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.*;
import java.util.stream.Collectors;

public class Service {

    private final boolean verbose;
    private Map<Integer, Supers> heroes;
    private Map<Integer, Supers> villains;
    private Random random = new Random();

    public Service(boolean verbose) {
        this.verbose = verbose;
    }

    private Completable start() {
        Vertx vertx = Vertx.vertx();
        Router router = Router.router(vertx);
        router.get("/heroes").handler(this::getAllHeroes);
        router.get("/villains").handler(this::getAllVillains);
        router.get("/heroes/random").handler(this::getRandomHero);
        router.get("/heroes/:id").handler(this::getHeroById);
        router.get("/villains/random").handler(this::getRandomVillain);
        router.get("/villains/:id").handler(this::getVillainById);

        return vertx.fileSystem().rxReadFile("src/main/resources/characters.json")
                .map(buffer -> buffer.toJsonArray().stream().map(o -> new Supers((JsonObject) o)).collect(Collectors.toList()))
                .doOnSuccess(list -> {
                    if (verbose) {
                        System.out.println("Loaded " + list.size() + " heroes and villains");
                    }
                })
                .doOnSuccess(list -> {
                    this.villains = list.stream().filter(Supers::isVillain).collect(
                            HashMap::new, (map, superStuff) -> map.put(superStuff.getId(), superStuff), HashMap::putAll);
                    this.heroes = list.stream().filter(e -> ! e.isVillain()).collect(
                            HashMap::new, (map, superStuff) -> map.put(superStuff.getId(), superStuff), HashMap::putAll);
                })
                .flatMap(x -> vertx.createHttpServer()
                        .requestHandler(router::accept)
                        .rxListen(8080))
                .toCompletable();
    }

    private void getAllHeroes(RoutingContext rc) {
        rc.response().end(heroes.values().stream()
                .collect(JsonObject::new,
                        (json, superStuff) -> json.put(Integer.toString(superStuff.getId()), superStuff.getName()),
                        JsonObject::mergeIn)
                .encodePrettily());
    }

    private void getAllVillains(RoutingContext rc) {
        rc.response().end(villains.values().stream()
                .collect(JsonObject::new,
                        (json, superStuff) -> json.put(Integer.toString(superStuff.getId()), superStuff.getName()),
                        JsonObject::mergeIn)
                .encodePrettily());
    }

    private void getHeroById(RoutingContext rc) {
        getById(rc, heroes);
    }

    private void getById(RoutingContext rc, Map<Integer, Supers> map) {
        String id = rc.pathParam("id");
        try {
            Integer value = Integer.valueOf(id);
            Supers supers = map.get(value);
            if (supers == null) {
                rc.response().setStatusCode(404).end("Unknown hero " + id);
            } else {
                rc.response().end(supers.toJson().encodePrettily());
            }
        } catch (NumberFormatException e) {
            rc.response().setStatusCode(404).end("Unknown hero " + id);
        }
    }

    private void getVillainById(RoutingContext rc) {
        getById(rc, villains);
    }

    private void getRandomHero(RoutingContext rc) {
        List<Supers> h = new ArrayList<>(heroes.values());
        int index = random.nextInt(h.size());
        Supers hero = h.get(index);
        if (verbose) {
            System.out.println("Selected hero " + hero);
        }
        rc.response().end(hero.toJson().encodePrettily());
    }

    private void getRandomVillain(RoutingContext rc) {
        List<Supers> h = new ArrayList<>(villains.values());
        int index = random.nextInt(h.size());
        Supers villain = h.get(index);
        if (verbose) {
            System.out.println("Selected villain " + villain);
        }
        rc.response().end(villain.toJson().encodePrettily());
    }

    public static void run() {
        new Service(true).start().blockingAwait();
    }

    public static void run(boolean verbose) {
        new Service(verbose).start().blockingAwait();
    }

    public static void main(String[] args) {
        Service service = new Service(true);
        service.start().subscribe();
    }
}
