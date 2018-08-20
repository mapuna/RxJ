package org.mapuna.ch5;

import org.mapuna.utils.Service;

import static org.mapuna.utils.Helpers.villains_names;

public class Ch05Code01 {
    public static void main(String[] args) {
        Service.run();

        villains_names()
                .filter(
                        name -> name.contains("Queen")
                )
                .subscribe(System.out::println);
    }
}
