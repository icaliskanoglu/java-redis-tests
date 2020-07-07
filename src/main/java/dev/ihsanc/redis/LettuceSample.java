package dev.ihsanc.redis;

import com.beust.jcommander.*;
import io.lettuce.core.*;
import io.lettuce.core.api.*;
import io.lettuce.core.cluster.*;
import org.apache.logging.log4j.*;

import java.time.*;


public class LettuceSample {


    private final Logger logger = LogManager.getLogger(this.getClass());

    private StatefulRedisConnection<String, String> connection;

    private RedisConfig config;

    public static void main(String[] args) {
        RedisConfig config = new RedisConfig();
        JCommander j = new JCommander(config);
        j.parse(args);

        LettuceSample sample = new LettuceSample(config);
        sample.connect();
    }

    public LettuceSample(RedisConfig config) {
        this.config = config;
    }

    void connect() {

        if (config.cluster) {
            RedisClusterClient clusterClient = getClusterClient();
            connection = (StatefulRedisConnection<String, String>) clusterClient.connect();
        } else {
            RedisClient client = getClient();
            connection = client.connect();
        }
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
                .withHost(config.host)
                .withPort(config.port)
                .withTimeout(Duration.ofSeconds(config.connectionTimeout))
                .withPassword(config.password)
                .withSsl(config.enableSSL)
                .withStartTls(config.enableTls)
                .withVerifyPeer(config.verifyPeer)
                .build();
        logger.info("RedisURI: " + uri.toString() + ", URI: " + uri.toURI().toString());
        return uri;
    }
}
