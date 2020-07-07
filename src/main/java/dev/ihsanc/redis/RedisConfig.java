package dev.ihsanc.redis;

import com.beust.jcommander.*;
import io.lettuce.core.*;

public class RedisConfig {
    @Parameter(names = {"--port", "-p"}, description = "Port")
    public Integer port = 6379;

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
    public boolean cluster = false;

    public Integer getPort() {
        return port;
    }

    public RedisConfig setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getHost() {
        return host;
    }

    public RedisConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public boolean isEnableTls() {
        return enableTls;
    }

    public RedisConfig setEnableTls(boolean enableTls) {
        this.enableTls = enableTls;
        return this;
    }

    public boolean isEnableSSL() {
        return enableSSL;
    }

    public RedisConfig setEnableSSL(boolean enableSSL) {
        this.enableSSL = enableSSL;
        return this;
    }

    public boolean isVerifyPeer() {
        return verifyPeer;
    }

    public RedisConfig setVerifyPeer(boolean verifyPeer) {
        this.verifyPeer = verifyPeer;
        return this;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public RedisConfig setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RedisConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isCluster() {
        return cluster;
    }

    public RedisConfig setCluster(boolean cluster) {
        this.cluster = cluster;
        return this;
    }
}
