package org.mapuna.ch5;

import io.reactivex.Flowable;

public class Ch05Code05 {
    public static void main(String[] args) {
        Flowable<Integer> floableStream = Flowable.range(0, 11);

        floableStream
                .scan(0, (last_result, item) -> last_result + item)
                .subscribe(i -> System.out.println("[Scan] Got " + i));

        floableStream
                .reduce(0, (last_result, item) -> last_result + item)
                .subscribe(i -> System.out.println("[Reduce] Got " + i));
    }
}
