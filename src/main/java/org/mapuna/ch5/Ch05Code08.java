package org.mapuna.ch5;

import org.mapuna.utils.Supers;

import static org.mapuna.utils.Helpers.villains;

/**
 * Mapping a stream to a collection and vice-versa
 * toList (creating a list of items), toMap building a map and
 * to toMultiMap building a Map<K, Collection<V>>
 *
 * To transform a collection into a stream: Observable.fromIterable or
 * Flowable.{fromIterable, fromArray}
 */
public class Ch05Code08 {
    public static void main(String[] args) {
        villains()
                .map(Supers::getName)
                .toList()
                .subscribe(list -> System.out.println("There are " + list.size() + " villains."));
    }
}
