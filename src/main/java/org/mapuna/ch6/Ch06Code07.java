package org.mapuna.ch6;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.mapuna.utils.Supers;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * In many cases, we interact with non-Rx implementations that have their own
 * async variant, e.g., AsynchronousFileChannel from the JDK allows us to read a
 * file asynchronously, and requires a listener from us, which is notified of
 * success/failure.
 */
public class Ch06Code07 extends AbstractSuperAPI {

    @Override
    public Flowable<Supers> load() {
        File file = new File("src/main/resources/characters.json");
        return Single.<ByteBuffer>create(
                emitter -> {
                    AsynchronousFileChannel channel = AsynchronousFileChannel.open(file.toPath());
                    ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

                    channel.read(buffer, 0, null, new CompletionHandler<Integer, Void>() {
                        @Override
                        public void completed(Integer result, Void attachment) {
                            try {
                                channel.close();
                            } catch (IOException ex) {
                                emitter.onError(ex);
                            }
                            emitter.onSuccess(buffer);
                        }

                        @Override
                        public void failed(Throwable error, Void attachment) {
                            try{
                                channel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            emitter.onError(error);
                        }
                    });
                }
        )
                .map(buffer -> new String(buffer.array(), StandardCharsets.UTF_8))
                .map(JsonArray::new)
                .flatMapPublisher(Flowable::fromIterable)
                .cast(JsonObject.class)
                .map(json -> json.mapTo(Supers.class));
    }

    public static void main(String[] args) throws InterruptedException {
        new Ch06Code07()
                .load()
                .subscribe(
                        System.out::println,
                        Throwable::printStackTrace
                );
        Thread.sleep(2000);
    }
}
