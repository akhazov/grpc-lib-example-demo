package com.akhazov.grpc.userservice.service.validation;

import build.buf.protovalidate.ValidationResult;
import build.buf.protovalidate.Validator;
import com.akhazov.grpc.userservice.error.Error;
import com.akhazov.grpc.userservice.error.ServiceValidationException;
import com.google.protobuf.GeneratedMessageV3;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

@Slf4j
@GrpcGlobalServerInterceptor
@RequiredArgsConstructor
public class ValidationInterceptor implements ServerInterceptor {

    private final Validator requestValidator;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(next.startCall(call, headers)) {
            @SneakyThrows
            @Override
            public void onMessage(ReqT message) {
                log.info("Вызов метода: " + call.getMethodDescriptor().getFullMethodName());
                log.info("Входные параметры: " + message);
                ValidationResult validate = requestValidator.validate((GeneratedMessageV3) message);
                if(!validate.isSuccess()) throw new ServiceValidationException(Error.REQUEST_VALIDATION_ERROR, validate.getViolations());
                super.onMessage(message);
            }
        };
    }
}
