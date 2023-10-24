package org.task2.grpc.service;

import io.grpc.stub.StreamObserver;
import org.task2.grpc.stubs.PingPongServiceGrpc;
import org.task2.grpc.stubs.PingRequest;
import org.task2.grpc.stubs.PongResponse;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {

    @Override
    public void ping(PingRequest request, StreamObserver<PongResponse> responseStreamObserver) {
        System.out.println("preparing pong....");
        var response = PongResponse.newBuilder().setPong("pong").build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
        System.out.println("pong sent");
    }
}
