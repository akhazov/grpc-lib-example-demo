package com.akhazov.grpc.userservice.error.handler;

import build.buf.validate.Violation;
import com.akhazov.grpc.common.ErrorDetail;
import com.akhazov.grpc.userservice.error.ServiceException;
import com.akhazov.grpc.userservice.error.ServiceValidationException;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Обработчик ошибок, возникающих при grpc запросах сервиса.
 * Подробности об ошибке передаются в метаданных.
 */
@Slf4j
@GrpcAdvice
public class ExceptionHandler {

    /**
     * Обработка бизнес ошибок.
     */
    @GrpcExceptionHandler
    public StatusRuntimeException serviceExceptionHandler(ServiceException exception) {
        Metadata.Key<ErrorDetail> key = ProtoUtils.keyForProto(ErrorDetail.getDefaultInstance());
        ErrorDetail value = ErrorDetail.newBuilder()
                .setErrorCode(exception.getError().getCode())
                .setMessage(exception.getMessage())
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

    /**
     * Обработка ошибок валидации запросов.
     */
    @GrpcExceptionHandler
    public StatusRuntimeException validationExceptionHandler(ServiceValidationException exception) {
        Metadata.Key<ErrorDetail> key = ProtoUtils.keyForProto(ErrorDetail.getDefaultInstance());
        Map<String, String> messages = exception.getViolations().stream()
                .collect(Collectors.toMap(Violation::getConstraintId, Violation::getMessage));
        ErrorDetail response = ErrorDetail.newBuilder()
                .setErrorCode(exception.getError().getCode())
                .setMessage(exception.getError().getDescription())
                .putAllMetadata(messages)
                .build();
        Metadata metadata = new Metadata();
        metadata.put(key, response);
        return exception.getError()
                .getStatus()
                .withCause(exception)
                .withDescription(exception.getMessage())
                .asRuntimeException(metadata);
    }

    /**
     * Обработка общих случаев возникновения исключений.
     */
    @GrpcExceptionHandler
    public StatusRuntimeException exceptionHandler(Exception exception) {
        Metadata metadata = Status.trailersFromThrowable(exception);
        return Status.fromThrowable(exception)
                .withDescription(exception.getMessage())
                .asRuntimeException(metadata);
    }

}
