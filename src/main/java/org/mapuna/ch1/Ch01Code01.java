package org.mapuna.ch1;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import java.util.Arrays;
import java.util.List;

public class Ch01Code01 {
    private static List<String> SUPER_HEROES = Arrays.asList(
            "Superman",
            "Batman",
            "Aquaman",
            "Asterix",
            "Ironman"
    );

    public static void main(String[] args) {
         Observable<String> stream = Observable.fromIterable(SUPER_HEROES);

         /*
         Snippet 1: Following is shortened to Snippet 2 (Lambda)
          */
         stream.subscribe(new Consumer<String>() {
             @Override
             public void accept(String s) throws Exception {
                 System.out.println(s);
             }
         });
        System.out.println();
         /*
         Snippet 2: Following is shortened to snippet 3 (Replace Lambda with method ref)
          */
         stream.subscribe(s -> System.out.println(s));

        System.out.println();
         /*
         Snippet 3
          */
         stream.subscribe(System.out::println);
    }
}
