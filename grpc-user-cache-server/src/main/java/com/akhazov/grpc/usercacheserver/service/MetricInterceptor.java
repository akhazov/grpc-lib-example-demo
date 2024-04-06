package com.akhazov.grpc.usercacheserver.service;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MetricInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("Вызов перехватчика перед.");
        ServerCall.Listener<ReqT> reqTListener = serverCallHandler.startCall(interceptResponse(serverCall), metadata);
        log.info("Вызов перехватчика после.");

        return reqTListener;
    }

    private <ReqT, RespT> ServerCall<ReqT, RespT> interceptResponse(ServerCall<ReqT, RespT> serverCall) {
        return new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
            @Override
            public void sendMessage(RespT message) {
                log.info("Ответ сервиса " + message);
                super.sendMessage(message);
            }

            @Override
            public void close(Status status, Metadata trailers) {
                log.info("Ответ сервиса статус " + status);
                super.close(status, trailers);
            }

        };
    }

}
