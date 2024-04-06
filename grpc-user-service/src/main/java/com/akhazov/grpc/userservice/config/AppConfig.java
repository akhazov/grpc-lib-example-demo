package com.akhazov.grpc.userservice.config;

import build.buf.protovalidate.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Validator grpcRequestValidator() {
        return new Validator();
    }

}
