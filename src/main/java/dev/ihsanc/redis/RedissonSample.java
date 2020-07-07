package dev.ihsanc.redis;

import com.beust.jcommander.*;

import org.apache.logging.log4j.*;
import org.redisson.*;
import org.redisson.api.*;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.config.*;

import java.util.concurrent.*;

public class RedissonSample {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private RedisConfig redisConfig;
    private RedissonClient redisson;
    org.redisson.api.redisnode.RedisNodes nodes;

    public static void main(String[] args) {
        RedisConfig config = new RedisConfig();
        JCommander j = new JCommander(config);
        j.parse(args);

        RedissonSample sample = new RedissonSample(config);
        sample.connect();
    }

    public RedissonSample(RedisConfig config) {
        this.redisConfig = config;
    }

    void connect() {
        redisson = Redisson.create(getConfig());
        boolean b = redisson.getRedisNodes(nodes).pingAll();
        if (!b) {
            throw new RuntimeException("Could not connect to redis!");
        }
        logger.info("PING: PONG");
    }

    Config getConfig() {
        Config config = new Config();
        String schema = redisConfig.enableSSL ? "rediss://" : "redis://";
        String adress = schema + redisConfig.host + ":" + redisConfig.port;
        if (redisConfig.cluster) {
            config.useClusterServers()
                    .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(redisConfig.connectionTimeout))
                    .setSslEnableEndpointIdentification(redisConfig.enableSSL)
                    .setPassword(redisConfig.password)
                    .addNodeAddress(adress);
            nodes = RedisNodes.CLUSTER;
        } else {
            config.useSingleServer()
                    .setConnectTimeout((int) TimeUnit.SECONDS.toMillis(redisConfig.connectionTimeout))
                    .setSslEnableEndpointIdentification(redisConfig.enableSSL)
                    .setPassword(redisConfig.password)
                    .setAddress(adress);
            nodes = RedisNodes.SINGLE;
        }
        return config;
    }
}
