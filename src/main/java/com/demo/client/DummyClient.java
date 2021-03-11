package com.demo.client;

import com.demo.grpc.Dummy;
import com.demo.grpc.DummyRequest;
import com.demo.grpc.DummyResponse;
import com.demo.grpc.DummyServiceGrpc;
import com.demo.server.DummyServiceImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DummyClient {
    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5051)
                .usePlaintext()
                .build();

        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        DummyRequest dummyRequest = DummyRequest.newBuilder()
                .setName("Pasha")
                .build();

        DummyResponse dummyResponse = syncClient.dummy(dummyRequest);


        System.out.println(dummyResponse.getResult());

        channel.shutdown();

    }
}
