package com.akhazov.gateway.handler;

import com.akhazov.grpc.clientservice.ApiError;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(StatusRuntimeException exception) {
        Metadata trailers = exception.getTrailers();
        ApiError apiError = trailers.get(ProtoUtils.keyForProto(ApiError.getDefaultInstance()));
        log.error(exception.getMessage(), exception);
        return apiError == null
                ? ResponseEntity.internalServerError().body(new ErrorResponse("0000", exception.getMessage(), null))
                : ResponseEntity.badRequest().body(new ErrorResponse(apiError.getErrorCode(), exception.getMessage(), apiError.getErrorMessageList()));
    }

    public record ErrorResponse(
          String errorCode,
          String description,
          List<String> messages
    ) {}

}
