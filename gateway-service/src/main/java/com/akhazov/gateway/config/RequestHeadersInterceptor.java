package com.akhazov.gateway.config;

import io.grpc.*;
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;
import org.slf4j.MDC;

@GrpcGlobalClientInterceptor
public class RequestHeadersInterceptor implements ClientInterceptor {
    static final Metadata.Key<String> RQ_UID = Metadata.Key.of("RqUid", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                headers.put(RQ_UID, MDC.get("RqUid"));
                super.start(responseListener, headers);
            }
        };
    }
}
