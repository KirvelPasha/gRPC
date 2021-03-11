package com.demo;

import com.demo.server.DummyServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(5051)
                .addService(new DummyServiceImpl())
                .build();

        System.out.println("Server start");
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread( () -> {
            server.shutdown();
        }));

        server.awaitTermination();
    }
}
