package com.akhazov.grpcuserservice.error.handler;

import build.buf.validate.Violation;
import com.akhazov.grpc.clientservice.ApiError;
import com.akhazov.grpcuserservice.error.ServiceException;
import com.akhazov.grpcuserservice.error.ServiceValidationException;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import java.util.List;

@Slf4j
@GrpcAdvice
public class ExceptionHandler {

    @GrpcExceptionHandler
    public StatusRuntimeException serviceExceptionHandler(ServiceException exception) {
        Metadata.Key<ApiError> key = ProtoUtils.keyForProto(ApiError.getDefaultInstance());
        ApiError value = ApiError.newBuilder()
                .setErrorCode(exception.getError().getCode())
                .addErrorMessage(exception.getMessage())
                .build();
        Metadata metadata = new Metadata();
        metadata.put(key, value);
        log.warn(exception.getMessage());
        return exception.getError()
                .getStatus()
                .withDescription(exception.getMessage())
                .withCause(exception)
                .asRuntimeException(metadata);
    }

    @GrpcExceptionHandler
    public StatusRuntimeException validationExceptionHandler(ServiceValidationException exception) {
        Metadata.Key<ApiError> key = ProtoUtils.keyForProto(ApiError.getDefaultInstance());
        List<String> messages = exception.getViolations().stream()
                .map(Violation::getMessage)
                .toList();
        ApiError response = ApiError.newBuilder()
                .setErrorCode(exception.getError().getCode())
                .addAllErrorMessage(messages)
                .build();
        Metadata metadata = new Metadata();
        metadata.put(key, response);
        log.warn("Не валидные входные параметры: " + messages);
        return exception.getError()
                .getStatus()
                .withCause(exception)
                .withDescription(exception.getMessage())
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
