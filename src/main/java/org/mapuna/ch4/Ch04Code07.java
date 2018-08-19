package org.mapuna.ch4;

import io.vertx.reactivex.core.buffer.Buffer;

import static org.mapuna.utils.Helpers.fs;

public class Ch04Code07 {
    public static void main(String[] args) {
        // Use rxWriteFile to write a message to a file
        // This method accept a buffer, create a buffer with Buffer.buffer("message")
        // Don't forget to subscribe
        fs().rxWriteFile(
                "src/main/resources/hello.txt",
                Buffer.buffer("hello")
        ).subscribe(
                () -> System.out.println("File Written."),
                Throwable::printStackTrace
        );
    }
}
