package com.demo.server;

import com.demo.grpc.DummyRequest;
import com.demo.grpc.DummyResponse;
import com.demo.grpc.DummyServiceGrpc;
import io.grpc.stub.StreamObserver;

public class DummyServiceImpl extends DummyServiceGrpc.DummyServiceImplBase {
    @Override
    public void dummy(DummyRequest request, StreamObserver<DummyResponse> responseObserver) {
        String name = request.getName();
        String result = "Hello " + name;

        DummyResponse dummyResponse = DummyResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(dummyResponse);
        //Complete RPC call
        responseObserver.onCompleted();
    }
}
