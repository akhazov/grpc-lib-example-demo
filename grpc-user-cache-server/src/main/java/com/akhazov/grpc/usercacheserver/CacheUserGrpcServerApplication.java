package com.akhazov.grpc.usercacheserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CacheUserGrpcServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheUserGrpcServerApplication.class, args);
	}

}
