package com.akhazov.grpc.usercacheserver.config;

import com.akhazov.grpc.clientservice.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class InvokerConfig {

    @Bean
    @ConfigurationProperties(prefix = "grpc.clients.configuration")
    public Map<String, ClientProperties> clientsConfiguration() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceInvoker(Map<String, ClientProperties> configuration) {
        ClientProperties clientProperties = configuration.get("grpc-user-service");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(clientProperties.getAddress(), clientProperties.getPort()).usePlaintext().build();
        return UserServiceGrpc.newBlockingStub(channel);
    }

    @Data
    public static class ClientProperties {
        private String address;
        private Integer port;
    }

}
