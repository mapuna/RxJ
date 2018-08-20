package org.mapuna.ch5;

import java.util.HashSet;

import static org.mapuna.utils.Helpers.heroes;

/**
 * FlatMap
 *
 * FlatMap takes each item emitted by the observable stream and maps it to another
 * stream (this is the map part). Then, it merges the emissions from the returned
 * streams into a single stream (it’s the flat part).
 */
public class Ch05Code06 {
    public static void main(String[] args) {
        heroes()
                .scan(
                        new HashSet<String>(),
                        (set, superStuff) -> {
                            set.addAll(superStuff.getSuperpowers());
                            return set;
                        }
                )
                .count()
                .subscribe(
                        number -> System.out.println("Heroes have " + number + " unique super powers.")
                );
    }
}
