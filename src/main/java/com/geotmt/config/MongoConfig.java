package com.geotmt.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mongodb配置
 */
@Configuration
public class MongoConfig {
    //覆盖默认的MongoDbFacotry
//    @Bean
//    public MongoDbFactory DevMongoDbFactory() {
//        //客户端配置（连接数，副本集群验证）
//        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
//        builder.connectionsPerHost(properties.getMaxConnectionsPerHost());
//        builder.minConnectionsPerHost(properties.getMinConnectionsPerHost());
//        if (properties.getReplicaSet() != null) {
//            builder.requiredReplicaSetName(properties.getReplicaSet());
//        }
//
//        builder.threadsAllowedToBlockForConnectionMultiplier(
//                properties.getThreadsAllowedToBlockForConnectionMultiplier());
//        builder.serverSelectionTimeout(properties.getServerSelectionTimeout());
//        builder.maxWaitTime(properties.getMaxWaitTime());
//        builder.maxConnectionIdleTime(properties.getMaxConnectionIdleTime());
//        builder.maxConnectionLifeTime(properties.getMaxConnectionLifeTime());
//        builder.connectTimeout(properties.getConnectTimeout());
//        builder.socketTimeout(properties.getSocketTimeout());
////        builder.socketKeepAlive(properties.getSocketKeepAlive());
//        builder.sslEnabled(properties.getSslEnabled());
//        builder.sslInvalidHostNameAllowed(properties.getSslInvalidHostNameAllowed());
//        builder.alwaysUseMBeans(properties.getAlwaysUseMBeans());
//        builder.heartbeatFrequency(properties.getHeartbeatFrequency());
//        builder.minHeartbeatFrequency(properties.getMinHeartbeatFrequency());
//        builder.heartbeatConnectTimeout(properties.getHeartbeatConnectTimeout());
//        builder.heartbeatSocketTimeout(properties.getHeartbeatSocketTimeout());
//        builder.localThreshold(properties.getLocalThreshold());
//​
//        MongoClientOptions mongoClientOptions = builder.build();
//​
//​
//        // MongoDB地址列表
//        List<ServerAddress> serverAddresses = new ArrayList<>();
//        for (String address : properties.getAddress()) {
//            String[] hostAndPort = address.split(":");
//            String host = hostAndPort[0];
//            Integer port = Integer.parseInt(hostAndPort[1]);
//​
//            ServerAddress serverAddress = new ServerAddress(host, port);
//            serverAddresses.add(serverAddress);
//        }
//​
//        // 连接认证
//        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(properties.getUsername(),
//                properties.getAuthenticationDatabase() != null ? properties.getAuthenticationDatabase() : properties.getDatabase(),
//                properties.getPassword().toCharArray());
//​
//​
//        //创建客户端和Factory
//        MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);
//        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, properties.getDatabase());
//​
//        return mongoDbFactory;
//    }

    @Bean
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
        Arrays.stream(addrss.split(",")).forEach(v -> {
                    ServerAddress serverAddress = new ServerAddress(v.split(":")[0], Integer.parseInt(v.split(":")[1]));
                    serverAddresses.add(serverAddress);
                });
        // 连接认证,三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential("","loan","".toCharArray());
        MongoClient mongoClient = new MongoClient(serverAddresses,mongoClientOptions) ;
        return new SimpleMongoDbFactory(mongoClient,"loan");
    }
}
