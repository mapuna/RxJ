Reactive Java (RxJava2) Exercises
---------------------------------

Source: [Rx Java 2 - Reactive Programming and Libraries](http://escoffier.me/rxjava-hol/)

Interesting things are in packages org.mapuna.{ch4, ch5, ch6, ch7, ch8}:
1. In ch4, we create streams.
2. In ch5, we apply operators.
3. In ch6, we create new APIs from a. other Rx APIs (code listing Ch06Code01 to Ch06Code07), b. from other Async but non-Rx APIs (code listing Ch06Code07), c. from other non-async source (code listing Ch06Code08). We also explore some intricacies of Rx APIs thereafter, and converting blocking code to Rx.
4. In ch7, we learn about Rx specific schedulers and concurrency.
    - When creating an RX API, it is key to realize that the threading model of our API and the threading of the application consuming our API can be different and might interfere:
        * The threading model of our API is defined by the APIs our implementation uses
        * The threading model of the application is defined by the runtime hosting the application
    - Here are a few examples:
        * our API is implemented with a non-blocking event loop library
        * our API is implemented with JDBC (a thread blocking API)
        * the application runs in a main
        * the application uses a thread-per-request model in a servlet container
        * the application runs in a non-blocking event loop server
    - As API designer, we have two responsibilities:
        * understand the concurrency of our implementation
        * properly document the concurrency of our API. Rx won’t exempt we from writing documentation; quite the opposite actually.
    - In ch7, we cover the execution model behind Rx and how Schedulers helps we manage concurrency. 
        * First rule: Schedulers do not schedule, so don’t be confused by the name.
    - But before diving into schedulers, we figure out how Rx emission works. Remember, an emission is when an observed stream is pushing a new item to an observer/subscriber.
5. In ch8, we learn testing streams.