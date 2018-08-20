package org.mapuna.ch5;

import io.reactivex.Flowable;
import org.mapuna.utils.Supers;

import static org.mapuna.utils.Helpers.heroes;
import static org.mapuna.utils.Helpers.villains;

/**
 * Merge and Zip: operators to merge streams or to associate items from different streams.
 */
public class Ch05Code12 {
    public static void main(String[] args) {
        Flowable<String> villains_superpowers =
                villains().map(Supers::getSuperpowers)
                        .flatMap(Flowable::fromIterable);
        Flowable<String> heroes_superpowers =
                heroes().map(Supers::getSuperpowers)
                        .flatMap(Flowable::fromIterable);

        // Merge both stream using the `mergeWith` operator
        villains_superpowers.mergeWith(heroes_superpowers)
                // Filter out duplicates using the `distinct` operator
                .distinct()
                // Count the number of item using the count operator
                .count()
                // Subscribe to print the number of unique super powers
                .subscribe(System.out::println);
    }
}
