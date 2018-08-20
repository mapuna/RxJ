package org.mapuna.ch5;

import java.util.HashSet;

import static org.mapuna.utils.Helpers.villains;

public class Ch05Code07 {
    public static void main(String[] args) {
        villains()
                .reduce(
                        new HashSet<String>(),
                        (set, superStuff) -> {
                            set.addAll(superStuff.getSuperpowers());
                            return set;
                        }
                )
                .subscribe(
                        s -> System.out.println("Villains have " + s.size() + " superpowers!")
                );
    }
}
