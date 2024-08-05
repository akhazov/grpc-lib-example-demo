package com.akhazov.grpc.userservice.config.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

/***
 * Пример перехватчика для логирования gRPC запросов.
 */
@Slf4j
@GrpcGlobalServerInterceptor
public class RequestLoggingInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(next.startCall(call, headers)) {
            @Override
            public void onMessage(ReqT message) {
                log.info("Вызов gRPC метода: {}", call.getMethodDescriptor().getBareMethodName());
                super.onMessage(message);
            }

            @Override
            public void onComplete() {
                log.info("Завершение работы gRPC метода: {}", call.getMethodDescriptor().getBareMethodName());
                super.onComplete();
            }
        };
    }

}
