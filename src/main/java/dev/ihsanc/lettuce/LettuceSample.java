package dev.ihsanc.lettuce;

import com.beust.jcommander.*;
import io.lettuce.core.*;
import io.lettuce.core.api.*;
import io.lettuce.core.cluster.*;
import io.lettuce.core.cluster.api.*;
import org.apache.logging.log4j.*;

import java.time.*;


public class LettuceSample {

    @Parameter(names = {"--port", "-p"}, description = "Port")
    public Integer port = 6381;

    @Parameter(names = {"--host", "-h"}, description = "Host")
    public String host = "localhost";

    @Parameter(names = {"--enable-tls"}, description = "Enable TLS", arity = 1)
    public boolean enableTls = false;

    @Parameter(names = {"--enable-ssl"}, description = "Enable SSL", arity = 1)
    public boolean enableSSL = false;

    @Parameter(names = {"--verify-peer"}, description = "Verify Peer", arity = 1)
    public boolean verifyPeer = true;

    @Parameter(names = {"--connection-timeout", "=ct"}, description = "Connection timeout")
    public long connectionTimeout = RedisURI.DEFAULT_TIMEOUT;

    @Parameter(names = {"--password", "-pw"}, description = "Password")
    public String password;

    @Parameter(names = {"--cluster"}, description = "Is Clluster", arity = 1)
    public boolean cluster = true;

    private final Logger logger = LogManager.getLogger(this.getClass());

    private StatefulRedisClusterConnection<String, String> clusterConnection;
    private StatefulRedisConnection<String, String> connection;

    public static void main(String[] args) {
        LettuceSample sample = new LettuceSample();
        JCommander j = new JCommander(sample);
        j.parse(args);

        if (sample.cluster) {
            sample.connectToCluster();
        } else {
            sample.connect();
        }
    }

    void connectToCluster() {
        RedisClusterClient clusterClient = getClusterClient();
        clusterConnection = clusterClient.connect();
        String pong = clusterConnection.sync().ping();
        logger.info("PING: " + pong);
    }

    void connect() {
        RedisClient client = getClient();
        connection = client.connect();
        String pong = connection.sync().ping();
        logger.info("PING: " + pong);
    }


    RedisClusterClient getClusterClient() {
        RedisClusterClient client = RedisClusterClient.create(getRedisURIByHost());
        return client;
    }

    RedisClient getClient() {
        RedisClient client = RedisClient.create(getRedisURIByHost());
        return client;
    }

    RedisURI getRedisURIByHost() {

        RedisURI uri = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withTimeout(Duration.ofSeconds(connectionTimeout))
                .withPassword(password)
                .withSsl(enableSSL)
                .withStartTls(enableTls)
                .withVerifyPeer(verifyPeer)
                .build();
        logger.info("RedisURI: " + uri.toString() + ", URI: " + uri.toURI().toString());
        return uri;
    }
}
