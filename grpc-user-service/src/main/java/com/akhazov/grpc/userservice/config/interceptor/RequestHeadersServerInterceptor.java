package com.akhazov.grpc.userservice.config.interceptor;

import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.slf4j.MDC;

/***
 * Перехватчик grpc запросов к серверу. Позволяет обогащать MDC контекст для расширенного логирования запросов к приложению.
 * Текущая реализация достает из метаданных идентификатор запроса и добавляет его в контекст MDC для отображения в логах.
 * @link logback-spring.xml Паттерн отображения в тэге <pattern> (%mdc).
 */
@GrpcGlobalServerInterceptor
public class RequestHeadersServerInterceptor implements ServerInterceptor {

    private static final String RQ_UID = "RqUid";
    private static final Metadata.Key<String> RQ_UID_KEY = Metadata.Key.of(RQ_UID, Metadata.ASCII_STRING_MARSHALLER);
    @Override

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        return new SimpleForwardingServerCallListener<>(next.startCall(call, headers)) {
            @Override
            public void onMessage(ReqT message) {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable(RQ_UID, headers.get(RQ_UID_KEY))) {
                    super.onMessage(message);
                }
            }

            @Override
            public void onHalfClose() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable(RQ_UID, headers.get(RQ_UID_KEY))) {
                    super.onHalfClose();
                }
            }

            @Override
            public void onCancel() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable(RQ_UID, headers.get(RQ_UID_KEY))) {
                    super.onCancel();
                }
            }

            @Override
            public void onComplete() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable(RQ_UID, headers.get(RQ_UID_KEY))) {
                    super.onComplete();
                }
            }

            @Override
            public void onReady() {
                try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable(RQ_UID, headers.get(RQ_UID_KEY))) {
                    super.onReady();
                }
            }
        };
    }

}
