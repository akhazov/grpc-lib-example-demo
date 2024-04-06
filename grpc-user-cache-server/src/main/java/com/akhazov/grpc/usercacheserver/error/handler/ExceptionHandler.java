package com.akhazov.grpc.usercacheserver.error.handler;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.lognet.springboot.grpc.recovery.GRpcExceptionHandler;
import org.lognet.springboot.grpc.recovery.GRpcExceptionScope;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;

@GRpcServiceAdvice
public class ExceptionHandler {
    @GRpcExceptionHandler
    public Status exceptionHandler(StatusRuntimeException exception, GRpcExceptionScope scope) {
        Metadata metadata = Status.trailersFromThrowable(exception);
        scope.getResponseHeaders().merge(metadata);
        return Status.fromThrowable(exception);
    }
}
