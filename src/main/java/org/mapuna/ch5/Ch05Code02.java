package org.mapuna.ch5;

import org.mapuna.utils.Service;

import static org.mapuna.utils.Helpers.villains_names;

public class Ch05Code02 {
    public static void main(String[] args) {
        Service.run();

        // Extract 10 villains that are in the position 21 -> 31 in the stream
        villains_names()
                .skip(20)
                .take(10)
                .subscribe(System.out::println);
    }
}
