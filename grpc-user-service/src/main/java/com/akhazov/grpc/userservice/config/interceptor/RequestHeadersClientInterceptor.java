package com.akhazov.grpc.userservice.config.interceptor;

import io.grpc.*;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.slf4j.MDC;

/***
 * Перехватчик grpc запросов через grpc клиент. Позволяет обогащать запрос данными из MDC контекста приложения.
 * Текущая реализация достает из контекста MDC идентификатор запроса и добавляет в метаданные запроса.
 */
@GrpcGlobalClientInterceptor
public class RequestHeadersClientInterceptor implements ClientInterceptor {

    private static final String RQ_UID = "RqUid";
    private static final Metadata.Key<String> RQ_UID_KEY = Metadata.Key.of(RQ_UID, Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(RQ_UID_KEY, MDC.get(RQ_UID));
                super.start(responseListener, headers);
            }
        };
    }
}
