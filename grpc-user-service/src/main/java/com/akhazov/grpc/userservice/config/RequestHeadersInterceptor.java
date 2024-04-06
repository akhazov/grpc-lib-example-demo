package com.akhazov.grpc.userservice.config;

import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.slf4j.MDC;

@GrpcGlobalServerInterceptor
public class RequestHeadersInterceptor implements ServerInterceptor {
    static final Metadata.Key<String> RQ_UID = Metadata.Key.of("RqUid", Metadata.ASCII_STRING_MARSHALLER);
    @Override

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        return new SimpleForwardingServerCallListener<>(next.startCall(call, headers)) {
            @Override
            public void onMessage(ReqT message) {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable("RqUid", headers.get(RQ_UID))) {
                    super.onMessage(message);
                }
            }

            @Override
            public void onHalfClose() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable("RqUid", headers.get(RQ_UID))) {
                    super.onHalfClose();
                }
            }

            @Override
            public void onCancel() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable("RqUid", headers.get(RQ_UID))) {
                    super.onCancel();
                }
            }

            @Override
            public void onComplete() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable("RqUid", headers.get(RQ_UID))) {
                    super.onComplete();
                }
            }

            @Override
            public void onReady() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable("RqUid", headers.get(RQ_UID))) {
                    super.onReady();
                }
            }
        };
    }

}
