package com.demo.client;

import com.demo.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DummyClient {
    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 5051)
                .usePlaintext()
                .build();

//        requestUnaryService(channel);

//        requestStreamService(channel);

        requestClientStreamService(channel);

        channel.shutdown();
    }

    private static void requestUnaryService(ManagedChannel channel) {
        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        DummyRequest dummyRequest = DummyRequest.newBuilder()
                .setName("Pasha")
                .build();

        DummyResponse dummyResponse = syncClient.dummy(dummyRequest);

        System.out.println(dummyResponse.getResult());
    }

    private static void requestStreamService(ManagedChannel channel) {
        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        DummyManyTimesRequest dummyManyTimesRequest = DummyManyTimesRequest.newBuilder()
                .setName("Pasha")
                .build();

         syncClient.dummyManyTimes(dummyManyTimesRequest)
                 .forEachRemaining(dummyManyTimesResponse -> {
                     System.out.println(dummyManyTimesResponse.getResult());
                 });
    }

    private static void requestClientStreamService(ManagedChannel channel) {
        DummyServiceGrpc.DummyServiceStub asyncClient = DummyServiceGrpc.newStub(channel);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        StreamObserver<DummyManyTimesClientRequest>  dummyManyTimesClientRequestStreamObserver = asyncClient.dummyManyTimesClient(new StreamObserver<DummyManyTimesClientResponse>() {
            @Override
            public void onNext(DummyManyTimesClientResponse value) {
                //get Response
                System.out.println("Response from server");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                //done
                countDownLatch.countDown();

            }
        });
        dummyManyTimesClientRequestStreamObserver.onNext(DummyManyTimesClientRequest.newBuilder()
                .setName("Message 1")
                .build()
        );

        dummyManyTimesClientRequestStreamObserver.onNext(DummyManyTimesClientRequest.newBuilder()
                .setName("Message 2")
                .build()
        );

        dummyManyTimesClientRequestStreamObserver.onNext(DummyManyTimesClientRequest.newBuilder()
                .setName("Message 3")
                .build()
        );

        dummyManyTimesClientRequestStreamObserver.onCompleted();

        try {
            countDownLatch.await(3L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
