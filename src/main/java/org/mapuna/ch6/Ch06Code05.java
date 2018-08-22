package org.mapuna.ch6;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.mapuna.utils.Supers;

import java.util.Collections;

public class Ch06Code05 extends AbstractSuperAPI {
    public static void main(String[] args) {
        new Ch06Code05().hero()
                .subscribe(System.out::println, Throwable::printStackTrace);

        new Ch06Code05().villain()
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    @Override
    public Single<Supers> hero() {
        return load()
                .filter(s -> !s.isVillain())
                .toList()
                .map(list -> {
                    Collections.shuffle(list);
                    return list;
                })
                .flatMapObservable(Observable::fromIterable)
                .firstOrError();
    }

    @Override
    public Single<Supers> villain() {
        // I do not think this pipeline is good! There must be some other way to do this.
        return load()
                .filter(Supers::isVillain)
                .toList()
                .map(list -> {
                    Collections.shuffle(list);
                    return list;
                })
                .flatMapObservable(Observable::fromIterable)
                .firstOrError();
    }
}
