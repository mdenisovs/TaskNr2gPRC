package org.task2.grpc.servers;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.task2.grpc.client.PingClient;
import org.task2.grpc.stubs.PingRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PingServer {

    private Server server;

    public void startServer() {
        int port = 50052;
        try {
            server = ServerBuilder.forPort(port)
                    .build()
                    .start();
            System.out.println("ping server started...");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Clean server shutdown in case JVM was shutdown");
                    PingServer.this.stopServer();
                } catch (InterruptedException ignored) {
                }
            }));
        } catch (IOException ignored) {
        }
    }

    public void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PingServer orderServer = new PingServer();
        orderServer.startServer();

        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50052")
                .usePlaintext().build();
        var client = new PingClient(channel);
        var ping = PingRequest.newBuilder().setPing("ping?").build();
        System.out.println("sending ping...");
        System.out.println("Response: " + client.ping(ping).getPong());

        orderServer.blockUntilShutdown();
    }
}
