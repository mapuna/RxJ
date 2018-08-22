package org.mapuna.ch6;

import io.reactivex.Maybe;
import io.reactivex.Single;
import org.mapuna.utils.Supers;

public class Ch06Code06 extends AbstractSuperAPI {

    @Override
    public Maybe<Supers> findByName(String name) {
        return load()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .firstElement();
    }

    @Override
    public Single<Supers> findByNameOrError(String name) {
        return load()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .firstOrError();
    }

    public static void main(String[] args) {
        new Ch06Code06()
                .findByName("SuperGirl")
                .subscribe(
                        c -> System.out.println(
                                c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")
                        ),
                        Throwable::printStackTrace,
                        () -> System.out.println("Nope!")
                );

        new Ch06Code06()
                .findByName("Anupam")
                .subscribe(
                        c -> System.out.println(
                                c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")
                        ),
                        Throwable::printStackTrace,
                        () -> System.out.println("Nope! Anupam is a cool dude.")
                );

        new Ch06Code06().findByNameOrError("Yoda")
                .subscribe(
                        c -> System.out.println(
                                c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")
                        ),
                        Throwable::printStackTrace
                );

        new Ch06Code06().findByNameOrError("Anupam")
                .subscribe(
                        c -> System.out.println(
                                c.getName() + " is a super " + (c.isVillain() ? "villain" : "hero")
                        ),
                        t -> System.out.println(
                                "The lookup failed, as expected, Anupam is neither a super hero or a super villain"
                        )
                );
    }
}