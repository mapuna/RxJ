package org.mapuna.ch5;

import io.reactivex.Observable;
import org.mapuna.utils.Service;

import static org.mapuna.utils.Helpers.heroes_names;

/**
 * Default and Switch
 *
 * Sometimes, when you realize that your stream is empty, you want to inject some
 * sensible defaults. That’s what the defaultIfEmpty and switchIfEmpty operators
 * are doing.
 */
public class Ch05Code03 {
    public static void main(String[] args) {
        String name = "Iron Man";
        Service.run();
        heroes_names()
                .filter(s -> s.equals(name))
                .switchIfEmpty(
                        Observable.just(
                                "Oh", "no", "...", name, "is", "not", "a", "super", "hero"
                        ))
                .subscribe(System.out::println);
    }
}
