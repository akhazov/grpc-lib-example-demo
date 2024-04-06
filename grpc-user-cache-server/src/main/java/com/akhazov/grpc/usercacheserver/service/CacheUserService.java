package com.akhazov.grpc.usercacheserver.service;

import com.akhazov.grpc.clientservice.GetUserByIdGrpcRequest;
import com.akhazov.grpc.clientservice.UserServiceGrpc;
import com.akhazov.grpc.usercacheserver.GetCachedUserByIdGrpcResponse;
import com.akhazov.grpc.usercacheserver.mapper.UserServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheUserService {

    private final UserServiceGrpc.UserServiceBlockingStub userGrpcService;
    private final UserServiceMapper userServiceMapper;

    @Cacheable("getUserById")
    public GetCachedUserByIdGrpcResponse getCachedUserById(Integer userId) {
        var prepareRequest = GetUserByIdGrpcRequest.newBuilder().setUserId(userId).build();
        var userById = userGrpcService.getUserById(prepareRequest);
        log.info("Запись данных по пользователю {} в кэш", userId);
        return userServiceMapper.getUserToCachedResponse(userById);
    }

    @CacheEvict("getUserById")
    public void evictCachedUserById(Integer userId) {
        log.info("Удаление данных по пользователю {} из кэша", userId);
    }

}
