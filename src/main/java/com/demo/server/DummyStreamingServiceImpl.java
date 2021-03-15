package com.demo.server;

import com.demo.grpc.DummyManyTimesRequest;
import com.demo.grpc.DummyManyTimesResponse;
import com.demo.grpc.DummyResponse;
import com.demo.grpc.DummyServiceGrpc;
import io.grpc.stub.StreamObserver;

public class DummyStreamingServiceImpl extends DummyServiceGrpc.DummyServiceImplBase {
    @Override
    public void dummyManyTimes(DummyManyTimesRequest request, StreamObserver<DummyManyTimesResponse> responseObserver) {
        String name = request.getName();
        try {
            for (int i = 0; i < 10; i++) {
                String result = "Hello " + name + " " + i;
                DummyManyTimesResponse dummyManyTimesResponse = DummyManyTimesResponse.newBuilder()
                        .setResult(result)
                        .build();

                responseObserver.onNext(dummyManyTimesResponse);
                Thread.sleep(1000);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            responseObserver.onCompleted();
        }

    }
}
