package org.mapuna.ch5;

import org.mapuna.utils.Service;

import static org.mapuna.utils.Helpers.heroes_names;

/**
 * defaultIfEmpty injects a default value if the observed stream is empty.
 */
public class Ch05Code04 {
    public static void main(String[] args) {
        String name = "Asterix";
        Service.run();
        heroes_names()
                .filter(s -> s.equals(name))
                .defaultIfEmpty("Oh no... " + name + "is not a super hero")
                .subscribe(System.out::println);
    }
}
