package com.akhazov.grpcuserservice.config;

import build.buf.protovalidate.Config;
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
