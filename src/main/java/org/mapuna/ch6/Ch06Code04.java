package org.mapuna.ch6;

import io.reactivex.Flowable;
import org.mapuna.utils.Supers;

public class Ch06Code04 extends AbstractSuperAPI {
    public static void main(String[] args) {
        new Ch06Code04().heroes()
                .count()
                .subscribe(
                        i -> System.out.println(i + " heroes loaded"),
                        Throwable::printStackTrace
                );

        new Ch06Code04().villains()
                .count()
                .subscribe(
                        i -> System.out.println(i + " villains loaded"),
                        Throwable::printStackTrace
                );
    }

    @Override
    public Flowable<Supers> heroes() {
        return load()
                .filter(s -> !s.isVillain());
    }

    @Override
    public Flowable<Supers> villains() {
        return load()
                .filter(Supers::isVillain);
    }
}
