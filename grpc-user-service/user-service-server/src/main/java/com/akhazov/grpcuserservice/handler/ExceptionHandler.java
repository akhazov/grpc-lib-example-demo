package com.akhazov.grpcuserservice.handler;

import com.akhazov.grpc.clientservice.ApiError;
import com.akhazov.grpcuserservice.error.ServiceException;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ExceptionHandler {

    @GrpcExceptionHandler
    public StatusRuntimeException serviceExceptionHandler(ServiceException exception) {
        Metadata.Key<ApiError> key = ProtoUtils.keyForProto(ApiError.getDefaultInstance());
        ApiError response = ApiError.newBuilder()
                .setErrorCode(exception.getError().getCode())
                .setErrorMessage(exception.getMessage())
                .build();
        Metadata metadata = new Metadata();
        metadata.put(key, response);
        return exception.getError()
                .getStatus()
                .withDescription(exception.getMessage())
                .withCause(exception)
                .asRuntimeException(metadata);
    }

    @GrpcExceptionHandler
    public StatusRuntimeException exceptionHandler(Exception exception) {
        Metadata metadata = Status.trailersFromThrowable(exception);
        return Status.fromThrowable(exception)
                .withDescription(exception.getMessage())
                .asRuntimeException(metadata);
    }

}
