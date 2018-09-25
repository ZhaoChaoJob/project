package com.geotmt.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mongodb配置
 * https://blog.csdn.net/daibang2182/article/details/80585004
 */
@Configuration
public class MongoConfig {

    @Autowired
    private Environment env;

    //覆盖默认的MongoDbFacotry
    @Bean
    @Profile(value = {"dev"})
    public MongoDbFactory DevMongoDbFactory() {
        //客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(100);
        builder.minConnectionsPerHost(0);
//        builder.requiredReplicaSetName("reolicaName");
        builder.threadsAllowedToBlockForConnectionMultiplier(5);
        builder.serverSelectionTimeout(30000);
        builder.maxWaitTime(120000);
        builder.maxConnectionIdleTime(0);
        builder.maxConnectionLifeTime(0);
        builder.connectTimeout(10000);
        builder.socketTimeout(0);
//        builder.socketKeepAlive(properties.getSocketKeepAlive());
        builder.sslEnabled(false);
        builder.sslInvalidHostNameAllowed(false);
        builder.alwaysUseMBeans(false);
        builder.heartbeatFrequency(500);
        builder.minHeartbeatFrequency(500);
        builder.heartbeatConnectTimeout(20000);
        builder.heartbeatSocketTimeout(20000);
        builder.localThreshold(15);
        MongoClientOptions mongoClientOptions = builder.build();
//
//        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        String addrss = env.getProperty("spring.mongodb.addrss");
        String database = env.getProperty("spring.mongodb.database");
        Arrays.stream(addrss.split(",")).forEach(v -> {
            ServerAddress serverAddress = new ServerAddress(v.split(":")[0], Integer.parseInt(v.split(":")[1]));
            serverAddresses.add(serverAddress);
        });
        // 连接认证,三个参数分别为 用户名 数据库名称 密码
        MongoClient mongoClient = new MongoClient(serverAddresses,mongoClientOptions) ;
        return new SimpleMongoDbFactory(mongoClient,database);
    }

    //覆盖默认的MongoDbFacotry
    @Bean
    @Profile(value = {"test"})
    public MongoDbFactory TestMongoDbFactory() {
        //客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(100);
        builder.minConnectionsPerHost(0);
//        builder.requiredReplicaSetName("reolicaName");
        builder.threadsAllowedToBlockForConnectionMultiplier(5);
        builder.serverSelectionTimeout(30000);
        builder.maxWaitTime(120000);
        builder.maxConnectionIdleTime(0);
        builder.maxConnectionLifeTime(0);
        builder.connectTimeout(10000);
        builder.socketTimeout(0);
//        builder.socketKeepAlive(properties.getSocketKeepAlive());
        builder.sslEnabled(false);
        builder.sslInvalidHostNameAllowed(false);
        builder.alwaysUseMBeans(false);
        builder.heartbeatFrequency(500);
        builder.minHeartbeatFrequency(500);
        builder.heartbeatConnectTimeout(20000);
        builder.heartbeatSocketTimeout(20000);
        builder.localThreshold(15);
        MongoClientOptions mongoClientOptions = builder.build();
//
//        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        String addrss = env.getProperty("spring.mongodb.addrss");
        String database = env.getProperty("spring.mongodb.database");
        Arrays.stream(addrss.split(",")).forEach(v -> {
            ServerAddress serverAddress = new ServerAddress(v.split(":")[0], Integer.parseInt(v.split(":")[1]));
            serverAddresses.add(serverAddress);
        });
        // 连接认证,三个参数分别为 用户名 数据库名称 密码
        MongoClient mongoClient = new MongoClient(serverAddresses,mongoClientOptions) ;
        return new SimpleMongoDbFactory(mongoClient,database);
    }

    @Bean
    @Profile(value = {"pro"})
    public MongoDbFactory ProMongoDbFactory() {
        //客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(100);
        builder.minConnectionsPerHost(0);
//        builder.requiredReplicaSetName("reolicaName");
        builder.threadsAllowedToBlockForConnectionMultiplier(5);
        builder.serverSelectionTimeout(30000);
        builder.maxWaitTime(120000);
        builder.maxConnectionIdleTime(0);
        builder.maxConnectionLifeTime(0);
        builder.connectTimeout(10000);
        builder.socketTimeout(0);
//        builder.socketKeepAlive(properties.getSocketKeepAlive());
        builder.sslEnabled(false);
        builder.sslInvalidHostNameAllowed(false);
        builder.alwaysUseMBeans(false);
        builder.heartbeatFrequency(500);
        builder.minHeartbeatFrequency(500);
        builder.heartbeatConnectTimeout(20000);
        builder.heartbeatSocketTimeout(20000);
        builder.localThreshold(15);
        MongoClientOptions mongoClientOptions = builder.build();
//
//        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        String addrss = "localhost:27017";
        String database = env.getProperty("spring.mongodb.database");
        Arrays.stream(addrss.split(",")).forEach(v -> {
                    ServerAddress serverAddress = new ServerAddress(v.split(":")[0], Integer.parseInt(v.split(":")[1]));
                    serverAddresses.add(serverAddress);
                });
        // 连接认证,三个参数分别为 用户名 数据库名称 密码
        MongoClient mongoClient = new MongoClient(serverAddresses,mongoClientOptions) ;
        return new SimpleMongoDbFactory(mongoClient,database);
    }
}
