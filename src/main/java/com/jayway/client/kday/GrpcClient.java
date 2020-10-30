package main.java.com.jayway.client.kday;


import com.jayway.kday.grpc.ClueOne;
import com.jayway.kday.grpc.MyServiceGrpc;
import com.jayway.kday.grpc.Ping;
import com.jayway.kday.grpc.Pong;
import com.jayway.kday.grpc.PuzzleGrpc;
import com.jayway.kday.grpc.YourName;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class GrpcClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrpcClient.class);
    private static final String target = "grpc-puzzle-pvufxpciqa-lz.a.run.app";
    private static final int port = 443;

    /* BlockingStubs makes synchronous calls.
        The proto file can be modified to support asynchronous calls. */
    private final PuzzleGrpc.PuzzleBlockingStub blockingStub;
    private final ManagedChannel channel;

    private void startHere(YourName request) {
//         Sends an assembled request and awaits response.
        LOGGER.info("flot request: " + request);
        ClueOne response = blockingStub.startHere(request);
        LOGGER.info("Response received: " + response.getClue());
    }

    private GrpcClient() {
//        LOGGER.info("Creating client with target: " + target + ":" + port);
        channel = NettyChannelBuilder.forAddress(target, port).usePlaintext().build();
        blockingStub = PuzzleGrpc.newBlockingStub(channel);
    }

    private void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        GrpcClient client = new GrpcClient();
        try {
            // Assembles an RPC call.
//            Ping ping = Ping.newBuilder().setText("Ping!").build();
//            client.ping(ping);
            YourName yourName = YourName.newBuilder().setYourName("HaahJannik").build();
            client.startHere(yourName);
        } finally {
            client.shutdown();
        }
    }
}
