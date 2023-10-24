package org.task2.grpc.client;

import io.grpc.Channel;
import org.task2.grpc.stubs.PingPongServiceGrpc;
import org.task2.grpc.stubs.PingRequest;
import org.task2.grpc.stubs.PongResponse;

public class PingClient {

    private PingPongServiceGrpc.PingPongServiceBlockingStub serviceGrpcStub;

    public PingClient(Channel channel) {
        serviceGrpcStub = PingPongServiceGrpc.newBlockingStub(channel);
    }

    public PongResponse ping(PingRequest request) {
        return serviceGrpcStub.ping(request);
    }
}