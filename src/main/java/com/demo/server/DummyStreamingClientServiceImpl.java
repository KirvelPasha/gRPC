package com.demo.server;

import com.demo.grpc.DummyManyTimesClientRequest;
import com.demo.grpc.DummyManyTimesClientResponse;
import com.demo.grpc.DummyServiceGrpc;
import io.grpc.stub.StreamObserver;

public class DummyStreamingClientServiceImpl extends DummyServiceGrpc.DummyServiceImplBase {
    @Override
    public StreamObserver<DummyManyTimesClientRequest> dummyManyTimesClient(StreamObserver<DummyManyTimesClientResponse> responseObserver) {
        StreamObserver<DummyManyTimesClientRequest> dummyManyTimesClientRequestStreamObserver = new StreamObserver<DummyManyTimesClientRequest>() {
            StringBuilder result = new StringBuilder();
            @Override
            public void onNext(DummyManyTimesClientRequest value) {
                //client send message
                result.append("Hello ");
                result.append(value.getName());
                result.append(" !");
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                //client is done

                responseObserver.onNext(DummyManyTimesClientResponse.newBuilder()
                        .setResult(result.toString()).build()
                );

                responseObserver.onCompleted();
            }
        };

        return dummyManyTimesClientRequestStreamObserver;
    }
}
