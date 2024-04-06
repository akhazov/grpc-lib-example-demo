package com.akhazov.gateway.handler;

import com.akhazov.grpc.clientservice.ApiError;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(StatusRuntimeException exception) {
        Metadata trailers = exception.getTrailers();
        ApiError apiError = trailers.get(ProtoUtils.keyForProto(ApiError.getDefaultInstance()));
        ErrorResponse errorResponse = new ErrorResponse(apiError.getErrorCode(), exception.getMessage(), apiError.getErrorMessageList());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    public record ErrorResponse(
          String errorCode,
          String description,
          List<String> messages
    ) {}

}
