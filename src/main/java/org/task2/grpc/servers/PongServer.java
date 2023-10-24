package org.task2.grpc.servers;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.task2.grpc.service.PingPongServiceImpl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PongServer {

    private Server server;

    public void startServer() {
        int port = 50052;
        try {
            server = ServerBuilder.forPort(port)
                    .addService(new PingPongServiceImpl())
                    .build()
                    .start();
            System.out.println("pong server started...");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Clean server shutdown in case JVM was shutdown");

                try {
                    PongServer.this.stopServer();
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
        PongServer orderServer = new PongServer();
        orderServer.startServer();
        orderServer.blockUntilShutdown();
    }
}
