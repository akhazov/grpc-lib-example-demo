package com.akhazov.grpc.usercacheserver.api;

import com.akhazov.grpc.usercacheserver.CacheUserServiceGrpc;
import com.akhazov.grpc.usercacheserver.EvictUserGrpcRequest;
import com.akhazov.grpc.usercacheserver.GetCachedUserByIdGrpcRequest;
import com.akhazov.grpc.usercacheserver.GetCachedUserByIdGrpcResponse;
import com.akhazov.grpc.usercacheserver.service.CacheUserService;
import com.akhazov.grpc.usercacheserver.service.MetricInterceptor;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

@Slf4j
@GRpcService(interceptors = MetricInterceptor.class)
@RequiredArgsConstructor
public class CacheUserGrpcService extends CacheUserServiceGrpc.CacheUserServiceImplBase {

    private final CacheUserService cacheUserService;

    @Override
    public void getUserById(GetCachedUserByIdGrpcRequest request, StreamObserver<GetCachedUserByIdGrpcResponse> responseObserver) {
        GetCachedUserByIdGrpcResponse response = cacheUserService.getCachedUserById(request.getUserId());
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void evictUserById(EvictUserGrpcRequest request, StreamObserver<Empty> responseObserver) {
        cacheUserService.evictCachedUserById(request.getUserId());
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

}
